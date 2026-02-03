package com.saucedemo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized configuration management.
 * Reads from environment variables with .env file fallback for local development.
 *
 * Priority: System Environment Variables > .env file
 * Fails fast if required configuration is missing.
 *
 * Setup:
 * 1. Copy .env.example to .env
 * 2. Set SAUCE_USERNAME and SAUCE_PASSWORD
 */
public final class TestConfig {

    private static final Logger logger = LoggerFactory.getLogger(TestConfig.class);
    private static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()  // Don't fail if .env doesn't exist (CI uses env vars)
                .load();
        logger.info("Configuration loaded successfully");
    }

    private TestConfig() {
        // Prevent instantiation
    }

    // ==================== Credentials ====================

    public static String getUsername() {
        return getRequiredEnv("SAUCE_USERNAME");
    }

    public static String getPassword() {
        return getRequiredEnv("SAUCE_PASSWORD");
    }

    // ==================== URLs ====================

    public static String getBaseUrl() {
        String url = getOptionalEnv("BASE_URL", "https://www.saucedemo.com");
        // Normalize: remove trailing slash
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    // ==================== Browser Configuration ====================

    public static String getBrowser() {
        return getOptionalEnv("BROWSER", "chrome").toLowerCase();
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
        return Boolean.parseBoolean(getOptionalEnv("HEADLESS", "false"));
    }

    // ==================== Paths ====================

    public static String getScreenshotPath() {
        return getOptionalEnv("SCREENSHOT_PATH", "target/screenshots");
    }

    // ==================== Helper Methods ====================

    /**
     * Get required configuration value. Fails fast if not found.
     *
     * @param key Configuration key
     * @return The configuration value
     * @throws IllegalStateException if configuration is missing
     */
    private static String getRequiredEnv(String key) {
        String value = getEnv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(
                    key + " not configured. Set in .env file or system environment."
            );
        }
        return value;
    }

    /**
     * Get optional configuration value with default fallback.
     *
     * @param key          Configuration key
     * @param defaultValue Default value if not found
     * @return The configuration value or default
     */
    private static String getOptionalEnv(String key, String defaultValue) {
        String value = getEnv(key);
        if (value == null || value.isEmpty()) {
            logger.debug("Using default value for {}: {}", key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    /**
     * Get configuration value from environment.
     * Priority: System env > .env file
     *
     * @param key Configuration key
     * @return The value or null if not found
     */
    private static String getEnv(String key) {
        // Priority 1: System environment (for CI)
        String systemValue = System.getenv(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            return systemValue;
        }

        // Priority 2: .env file (for local development)
        String dotenvValue = dotenv.get(key);
        if (dotenvValue != null && !dotenvValue.isEmpty()) {
            return dotenvValue;
        }

        return null;
    }

    /**
     * Log current configuration (for debugging).
     * Masks sensitive values.
     */
    public static void logConfiguration() {
        logger.info("=== Test Configuration ===");
        logger.info("Base URL: {}", getBaseUrl());
        logger.info("Browser: {}", getBrowser());
        logger.info("Headless: {}", isHeadless());
        logger.info("Username: {}", getUsername());
        logger.info("Password: ****");
        logger.info("==========================");
    }
}
