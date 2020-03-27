package ch.bzz.brand.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


@ApplicationPath("/resource")

public class Config extends Application {
    private static final String PROPERTIES_PATH = "/home/bzz/webapp/brand.properties";
    private static Properties properties = null;


    @Override
    public Set<Class<?>> getClasses() {
        HashSet providers = new HashSet<Class<?>>();
        providers.add(TestService.class);
        return providers;
    }


    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }


    private static void readProperties() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {

            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }

    }

    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}