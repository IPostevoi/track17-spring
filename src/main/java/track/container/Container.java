package track.container;

import java.util.HashMap;
import java.util.List;
import java.lang.reflect.*;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.Property;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    Map<String, Bean> beansId = new HashMap<>();
    Map<String, Bean> beansName = new HashMap<>();


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
            if (field.getType().isPrimitive()) {
                for (Map.Entry<String, Property> entry: bean.getProperties().entrySet()) {
                    field.set(obj, Integer.parseInt(entry.getValue().getValue()));
                }
            } else {
                Object fieldObject = createObject(beansName.get(field.getType().getCanonicalName()));
                System.out.println(field.getType());
                field.set(obj, fieldObject);
            }
        }
        return obj;
    }

    public Object getById(String id) throws Exception {

        Object object = createObject(beansId.get(id));
        return object;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws Exception {

        Object object = createObject(beansName.get(className));
        return object;
    }
}
