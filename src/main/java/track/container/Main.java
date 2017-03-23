package track.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Root;

import java.lang.reflect.*;
import track.container.beans.Gear;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
           // for (Bean bean: beans) {
                //System.out.println(bean.getClassName());
            //}
            Class clazz = Class.forName(beans.get(1).getClassName());
            Object obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                System.out.println(field);
                field.setAccessible(true);
                System.out.println(Integer.parseInt(beans.get(1).getProperties().get("count").getValue()));
                field.set(obj, Integer.parseInt(beans.get(1).getProperties().get("count").getValue()));
                System.out.println(field.getType());
            }
            Gear test = (Gear) obj;
            System.out.println(test.getCount());

        } catch (InvalidConfigurationException ex) {
            System.out.println("InvalidConfigurationException");
        }

        /*ObjectMapper mapper;
        mapper = new ObjectMapper();
        Root root = mapper.readValue(new File("src/main/resources/config.json"), Root.class);
        List<Bean> beans = root.getBeans();
        System.out.println(beans.get(0).getId());
        */

    }
}
