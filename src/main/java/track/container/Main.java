package track.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Root;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            for (Bean bean : beans) {
                System.out.println("Working well");
                System.out.println("Id: " + bean.getProperties());
            }
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
