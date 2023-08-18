package com.moon.utils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class PropertiesManager {

    private static final Properties properties = new Properties();
    private static final String projectDir = System.getProperty("user.dir");

    public static LocalDate date = LocalDate.now();
    public static DateTimeFormatter formatter01 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String dateFormatted = date.format(formatter01);

    public static LocalTime time = LocalTime.now();
    public static DateTimeFormatter formatter02 = DateTimeFormatter.ofPattern("HH-mm-ss");
    public static String timeFormatted = time.format(formatter02).replace("-", "");

    public static String timestamp = dateFormatted + "-" + timeFormatted;

    private static Properties loadProperties() {
        try {
            InputStream input = new FileInputStream(projectDir + "/src/main/resources/config.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            LogGenerator.error(e.toString());
        }
        return properties;
    }

    public static String getProperty(String key) {
        return loadProperties().getProperty(key);
    }

    public static void setProperty(String key, String value) {
        loadProperties();
        try {
            OutputStream output = new FileOutputStream(projectDir + "/src/main/resources/config.properties");
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
            LogGenerator.error(e.toString());
        }
    }
}
