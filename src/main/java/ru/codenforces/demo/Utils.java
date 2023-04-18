package ru.codenforces.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Utils {
    private Utils(){

    }
    public static String getProperty(String property) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop.getProperty(property);
    }
}
