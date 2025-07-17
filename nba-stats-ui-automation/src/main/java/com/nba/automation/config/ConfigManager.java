package com.nba.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager to handle application properties
 * Implements singleton pattern for global configuration access
 */
public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;

    /**
     * Private constructor to prevent direct instantiation
     * Loads configuration from config.properties file
     */
    private ConfigManager() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("config.properties file not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Get singleton instance of ConfigManager
     * @return ConfigManager instance
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get base URL for the application
     * @return Base URL string
     */
    public String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get browser type for testing
     * @return Browser name (chrome, firefox, etc.)
     */
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    /**
     * Check if browser should run in headless mode
     * @return true if headless, false otherwise
     */
    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    /**
     * Get implicit wait timeout in seconds
     * @return Implicit wait timeout
     */
    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    /**
     * Get explicit wait timeout in seconds
     * @return Explicit wait timeout
     */
    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "30"));
    }

    /**
     * Get performance threshold in milliseconds
     * @return Performance threshold
     */
    public int getPerformanceThreshold() {
        return Integer.parseInt(getProperty("performance.threshold", "4000"));
    }
}