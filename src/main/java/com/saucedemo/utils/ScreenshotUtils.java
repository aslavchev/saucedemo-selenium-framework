package com.saucedemo.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Screenshot utility for test failures.
 */
public final class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final Path SCREENSHOT_DIR = Paths.get("target/screenshots");

    private ScreenshotUtils() {
    }

    /**
     * Capture screenshot, save to disk, attach to Allure.
     */
    public static void capture(WebDriver driver, String testName) {
        try {
            Files.createDirectories(SCREENSHOT_DIR);

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Save to disk
            String filename = testName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".png";
            Path path = SCREENSHOT_DIR.resolve(filename);
            Files.write(path, screenshot);

            // Attach to Allure
            Allure.addAttachment(testName, "image/png", new ByteArrayInputStream(screenshot), ".png");

            logger.info("Screenshot saved: {}", path);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", testName, e);
        }
    }
}
