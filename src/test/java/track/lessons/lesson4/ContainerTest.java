package track.lessons.lesson4;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.JsonConfigReader;
import track.container.Container;
import track.container.config.InvalidConfigurationException;
import track.container.beans.Gear;
import track.container.beans.Engine;
import track.container.beans.Car;
import track.container.config.InvalidReferencesException;

import java.io.*;
import java.util.List;


public class ContainerTest {

    @Test
    public void gearTestId() throws Exception {
        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Gear gear = (Gear) container.getById("gearBean");
            Assert.assertTrue(gear.getCount() == 6);
        } catch (InvalidConfigurationException ex) {
        }
    }


    @Test
    public void engineTestId() throws Exception {
        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Engine engine = (Engine) container.getById("engineBean");
            Assert.assertTrue(engine.getPower() == 200);
        } catch (InvalidConfigurationException ex) {
        }
    }

    @Test
    public void carTestId() throws Exception {
        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Car car = (Car) container.getById("carBean");
            Assert.assertTrue(car.getGear().getCount() == 6);
            Assert.assertTrue(car.getEngine().getPower() == 200);
        } catch (InvalidConfigurationException ex) {
        }
    }

    @Test
    public void carTestName() throws Exception {
        File configFile = new File("/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Car car = (Car) container.getByClass("track.container.beans.Car");
            Assert.assertTrue(car.getGear().getCount() == 6);
            Assert.assertTrue(car.getEngine().getPower() == 200);
        } catch (InvalidConfigurationException ex) {
        }
    }

    @Test
    public void gearTestRef() throws Exception {
        File configFile = new File(
                "/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/testConfigRef.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Gear gear= (Gear) container.getByClass("track.container.beans.Gear");
            Assert.assertTrue(gear.getCount() == 1000);
        } catch (InvalidConfigurationException ex) {
        }
    }

    @Test
    public void carTestAnotherRef() throws Exception {
        File configFile = new File(
                "/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/testConfigRef.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Car car = (Car) container.getById("carBean");
            Assert.assertTrue(car.getGear().getCount() == 1000);
            Assert.assertTrue(car.getEngine().getPower() == 1000);
        } catch (InvalidConfigurationException ex) {
        }
    }

    @Test
    public void testCircleRef() throws Exception {
        File configFile = new File(
                "/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/testConfigCircleRef.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Car car = (Car) container.getById("carBean");
        } catch (InvalidConfigurationException ex) {
        }
        catch (InvalidReferencesException ex) {
            String message = "Caught circle references!";
            Assert.assertTrue(message == "Caught circle references!");
        }
    }

    @Test
    public void singletonTest() throws Exception {
        File configFile = new File(
                "/home/bakla410/Desktop/TechnotrackJava/track17/src/main/resources/config.json");

        JsonConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            Car car1 = (Car) container.getById("carBean");
            Car car2 = (Car) container.getById("carBean");
            Assert.assertTrue(car1 == car2);
        } catch (InvalidConfigurationException ex) {}
    }
}
