package com.saucedemo.tests;

import com.saucedemo.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.saucedemo.listeners.TestListener;

import java.time.Duration;

/**
 * Base test class that all test classes should extend.
 * Handles WebDriver setup/teardown and common test configuration.
 */
@Listeners({TestListener.class})
public class BaseTest {

    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Get the WebDriver instance.
     * Used by TestListener for screenshot capture.
     */
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        TestConfig.logConfiguration();
    }

    @BeforeMethod(alwaysRun = true)
    @Step("Initialize browser and navigate to application")
    public void setUp() {
        logger.info("Setting up test...");
        driver = createDriver();
        configureDriver();
        navigateToBaseUrl();
    }

    @AfterMethod(alwaysRun = true)
    @Step("Close browser")
    public void tearDown() {
        logger.info("Tearing down test...");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Create WebDriver instance based on configuration.
     */
    private WebDriver createDriver() {
        String browser = TestConfig.getBrowser();
        boolean headless = TestConfig.isHeadless();

        logger.info("Creating {} driver (headless: {})", browser, headless);

        return switch (browser) {
            case "firefox" -> createFirefoxDriver(headless);
            case "chrome" -> createChromeDriver(headless);
            default -> {
                logger.warn("Unknown browser '{}', defaulting to Chrome", browser);
                yield createChromeDriver(headless);
            }
        };
    }

    private WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        // Recommended Chrome options for stability
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--incognito");

        // Disable automation flags (some sites detect these)
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // Disable password manager and breach detection dialogs
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        return new FirefoxDriver(options);
    }

    /**
     * Configure WebDriver timeouts.
     */
    private void configureDriver() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);  // Use explicit waits in BasePage
        driver.manage().window().maximize();
    }

    /**
     * Navigate to application base URL.
     */
    private void navigateToBaseUrl() {
        String baseUrl = TestConfig.getBaseUrl();
        logger.info("Navigating to: {}", baseUrl);
        driver.get(baseUrl);
    }
}
