package track.container;

import java.util.HashMap;
import java.util.List;
import java.lang.reflect.*;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.InvalidReferencesException;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClassName = new HashMap<>();
    private Map<String, Bean> beansId = new HashMap<>();    //Таблицы соответствий Id/Имени класса и бина
    private Map<String, Bean> beansName = new HashMap<>();
    private Map.Entry<String, Property> firstEntry = null;  //Переменная, сохраняющая ссылку на первую таблицу с  properties, отслеживает циклические ссылки


    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        for (Bean bean: beans) {
            beansId.put(bean.getId(), bean);
            beansName.put(bean.getClassName(), bean);
        }
    }
    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    private Object createObject(Bean bean) throws Exception {
        String className = bean.getClassName();
        Class clazz = Class.forName(className);
        Field[] fields = clazz.getDeclaredFields();
        Object obj = clazz.newInstance();
        for (Field field: fields) {
            field.setAccessible(true);
            if (field.getType().isPrimitive()) {                                       //если поле имеет примитивный тип
                for (Map.Entry<String, Property> entry: bean.getProperties().entrySet()) {
                    setFieldValue(entry, field, obj);
                }
            } else {                    //если поле имеет тип класс - создаем объект класса по ссылке и присваиваем полю
                Object fieldObject = createObject(beansName.get(field.getType().getCanonicalName()));
                field.set(obj, fieldObject);
            }
        }
        return obj;
    }

    private void setFieldValue(Map.Entry<String, Property> entry, Field field, Object obj) throws Exception {
        if (firstEntry == null) {
            firstEntry = entry;
        } else {
            if (firstEntry == entry) {           //Совпадение таблиц, переданных первый раз и впоследствиии обозначает появление циклической зависимости ссылок
                throw new InvalidReferencesException("Circle references!");
            }
        }

        //Проверка является ли поле примитивом или же содержит ссылку на другой бин
        if (entry.getValue().getType() == ValueType.VAL) {
            setFieldValValue(entry, field, obj);
            firstEntry = null;                                                      //Обнуление переменной класса
        } else {
            setFieldRefValue(entry, field, obj);
        }
    }

    private void setFieldValValue(Map.Entry<String, Property> entry, Field field, Object obj) throws Exception {

        field.set(obj, Integer.parseInt(entry.getValue().getValue()));  //Берет из словаря значение(Класс Property) и получает его значение Value
    }

    private void setFieldRefValue(Map.Entry<String, Property> entry, Field field, Object obj) throws Exception {

        Map<String, Property> refProperties = beansId.get(entry.getValue().getValue()).getProperties(); //Получает таблицу Porperties для класса, на который ведет ссылка
        for (Map.Entry<String, Property> entryField : refProperties.entrySet()) {   //Получает из таблицы выше итерируемую таблицу
            setFieldValue(entryField, field, obj);
        }
    }


    public Object getById(String id) throws Exception {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        } else {
            Object object = createObject(beansId.get(id));
            objByName.put(id, object);
            return object;
        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws Exception {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        } else {
            Object object = createObject(beansName.get(className));
            objByClassName.put(className, object);
            return object;
        }
    }
}
