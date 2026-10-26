package com.pizzeria.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Файл '" + CONFIG_FILE + "' не найден в classpath.");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить конфигурацию", e);
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return getRequiredProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static int getBrowserWidth() {
        return getIntProperty("browser.width", 1920);
    }

    public static int getBrowserHeight() {
        return getIntProperty("browser.height", 1080);
    }

    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 10);
    }

    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    public static String getTestUserEmail() {
        return getRequiredProperty("test.user.email");
    }

    public static String getTestUserPassword() {
        return getRequiredProperty("test.user.password");
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Обязательная настройка '" + key + "' не найдена");
        }
        return value.trim();
    }

    private static String getProperty(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return (value == null || value.isBlank()) ? defaultValue : value.trim();
    }

    private static int getIntProperty(String key, int defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Настройка '" + key + "' должна быть числом, а получено: '" + value + "'", e);
        }
    }
}