package com.saucedemo.listeners;

import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ScreenshotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for handling test lifecycle events.
 * Captures screenshots on failure and logs test execution status.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("========================================");
        logger.info("Starting test suite: {}", context.getName());
        logger.info("========================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("========================================");
        logger.info("Finished test suite: {}", context.getName());
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
        logger.info("========================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(">>> Starting test: {}", getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("<<< PASSED: {} ({}ms)",
                getTestName(result),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getTestName(result);
        logger.error("<<< FAILED: {}", testName);
        logger.error("Failure reason: {}", result.getThrowable().getMessage());

        // Capture screenshot on failure
        captureScreenshotOnFailure(result, testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("<<< SKIPPED: {}", getTestName(result));
        if (result.getThrowable() != null) {
            logger.warn("Skip reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("<<< FAILED (within success percentage): {}", getTestName(result));
    }

    private void captureScreenshotOnFailure(ITestResult result, String testName) {
        Object testInstance = result.getInstance();

        if (testInstance instanceof BaseTest baseTest && baseTest.getDriver() != null) {
            ScreenshotUtils.capture(baseTest.getDriver(), "FAILED_" + testName);
        }
    }

    /**
     * Get formatted test name including class and method.
     */
    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
    }
}
