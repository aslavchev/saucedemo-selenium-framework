package com.saucedemo.config;

/**
 * Centralized configuration management.
 * Reads from environment variables with sensible defaults for local development.
 */
public final class TestConfig {

    private TestConfig() {
        // Prevent instantiation
    }

    // ==================== Credentials ====================

    public static String getUsername() {
        return env("SAUCE_USERNAME", "standard_user");
    }

    public static String getPassword() {
        return env("SAUCE_PASSWORD", "secret_sauce");
    }

    // ==================== URLs ====================

    public static String getBaseUrl() {
        String url = env("BASE_URL", "https://www.saucedemo.com");
        // Normalize: remove trailing slash
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    // ==================== Browser Configuration ====================

    /**
     * Get browser name.
     * Priority: Maven property (-Dbrowser=firefox) > environment variable > chrome
     */
    public static String getBrowser() {
        String browserProperty = System.getProperty("browser");
        if (browserProperty != null) {
            return browserProperty.toLowerCase();
        }
        return env("BROWSER", "chrome").toLowerCase();
    }

    /**
     * Check if browser should run headless.
     * Priority: Maven property (-Dheadless=true) > environment variable > false
     */
    public static boolean isHeadless() {
        String headlessProperty = System.getProperty("headless");
        if (headlessProperty != null) {
            return Boolean.parseBoolean(headlessProperty);
        }
        return Boolean.parseBoolean(env("HEADLESS", "false"));
    }

    // ==================== Helper Methods ====================

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

}
