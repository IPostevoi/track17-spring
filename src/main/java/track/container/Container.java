package track.container;

import java.util.HashMap;
import java.util.List;
import java.lang.reflect.*;
import java.util.Map;
import org.apache.commons.lang3.ClassUtils;

import track.container.config.*;


public class Container {

    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClassName = new HashMap<>();
    private Map<String, Bean> beansId = new HashMap<>();    //Таблицы соответствий Id/Имени класса и бина
    private Map<String, Bean> beansName = new HashMap<>();
    private Map.Entry<String, Property> firstEntry = null;
    //Переменная, сохраняющая ссылку на первую таблицу с  properties, отслеживает циклические ссылки


    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {

        for (Bean bean: beans) {
            beansId.put(bean.getId(), bean);
            beansName.put(bean.getClassName(), bean);
        }
    }

    /* Создает объект по переданному бину, инициализирует его поля, если поле не примитивного типа, вызывается
     * рекурсивно для бина класса поля */
    private Object createObject(Bean bean) throws Exception {

        String className = bean.getClassName();
        Class clazz = Class.forName(className);
        Field[] fields = clazz.getDeclaredFields();
        Object obj = clazz.newInstance();
        for (Field field: fields) {
            field.setAccessible(true);
            if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || String.class == field.getType()) {
                for (Map.Entry<String, Property> fieldProperties: bean.getProperties().entrySet()) {
                    setFieldPrimitiveValue(clazz, fieldProperties, field, obj);
                }
            } else {                 //если поле имеет тип класс - создаем объект класса по ссылке и присваиваем полю
                for (Map.Entry<String, Property> fieldProperties: bean.getProperties().entrySet()) {
                    if (fieldProperties.getValue().getName().equals(field.getName())) {
                        setFieldClassValue(fieldProperties, field, obj);
                    }
                }
            }
        }
        return obj;
    }

    // Получает имя сеттера
    private String getSetterName(Field field) {

        String fieldName = field.getName();
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return setterName;
    }


    // Устанавливает значение в поле примитивного типа
    private void setFieldPrimitiveValue(Class clazz, Map.Entry<String, Property> fieldProperties, Field field, Object obj) throws Exception {

        if (firstEntry == null) {
            firstEntry = fieldProperties;  // При первом вызове устанавливает значение переданной таблицы properties
        } else {
            if (firstEntry == fieldProperties) {
                //Совпадение таблицы properties с переданной в первый раз - обозначает зацикливание ссылки
                throw new InvalidReferencesException("Circle references!");
            }
        }
        //Проверка является ли поле примитивом или же содержит ссылку на другой бин
        if (fieldProperties.getValue().getType() == ValueType.VAL) {
            setFieldValValue(clazz, fieldProperties, field, obj);
            firstEntry = null;                         //Обнуление переменной класса
        } else {
            setFieldRefValue(clazz, fieldProperties, field, obj);
        }
    }


    private void setFieldValValue(Class clazz, Map.Entry<String, Property> fieldProperties, Field field, Object obj) throws Exception {

        Method method = clazz.getMethod(getSetterName(field), field.getType());

        switch (field.getType().getName()) {
            case "int":
                method.invoke(obj, Integer.parseInt(fieldProperties.getValue().getValue()));
                break;
            case "double":
                method.invoke(obj, Double.parseDouble(fieldProperties.getValue().getValue()));
                break;
            case "byte":
                method.invoke(obj, Byte.parseByte(fieldProperties.getValue().getValue()));
                break;
            case "short":
                method.invoke(obj, Short.parseShort(fieldProperties.getValue().getValue()));
                break;
            case "long":
                method.invoke(obj, Long.parseLong(fieldProperties.getValue().getValue()));
                break;
            case "float":
                method.invoke(obj, Double.parseDouble(fieldProperties.getValue().getValue()));
                break;
            case "boolean":
                method.invoke(obj, Boolean.parseBoolean(fieldProperties.getValue().getValue()));
                break;
            default:
                method.invoke(obj, fieldProperties.getValue().getValue());
                break;
        }
    }






    private void setFieldRefValue(Class clazz, Map.Entry<String, Property> fieldProperties, Field field, Object obj) throws Exception {

        //Получает таблицу Porperties для класса, на который ведет ссылка, получает из таблицы итерируемую таблицу
        Map<String, Property> refProperties = beansId.get(fieldProperties.getValue().getValue()).getProperties();
        for (Map.Entry<String, Property> entryField : refProperties.entrySet()) {
            setFieldPrimitiveValue(clazz, entryField, field, obj);
        }
    }


    //Устанавливает значение в поле не примитивного типа
    private void setFieldClassValue(Map.Entry<String, Property> fieldProperties, Field field, Object obj) throws Exception {

        if (fieldProperties.getValue().getType() == ValueType.REF) {
            if (objByName.containsKey(fieldProperties.getValue().getValue())) { //Присваивает полю существующий объект
                field.set(obj, objByName.get(fieldProperties.getValue().getValue()));
            } else {                                                            //Создает новый объект
                Object fieldObject = createObject(beansName.get(field.getType().getCanonicalName()));
                field.set(obj, fieldObject);
            }
        } else {
            Class fieldClazz = Class.forName(field.getType().getCanonicalName());
            Object fieldObj = fieldClazz.newInstance();                            //Создаем объект типа поля
            Field[] fields = fieldClazz.getDeclaredFields();       //инициализируем значениями, переданными в properties
            for (Field fieldField: fields) {
                setFieldValValue(fieldClazz, fieldProperties, fieldField, fieldObj);
            }
            field.set(obj, fieldObj);
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
