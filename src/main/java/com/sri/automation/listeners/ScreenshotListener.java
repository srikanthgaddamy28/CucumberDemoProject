package com.sri.automation.listeners;

import com.sri.automation.utils.ScreenshotUtils;
import com.sri.automation.utils.WebDriverManager;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(ScreenshotListener.class);
    
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
                WebDriverManager.getDriver(), 
                testName
            );
            
            if (screenshotPath != null) {
                System.setProperty("screenshot.path", screenshotPath);
                logger.info("Screenshot captured for failed test: {} at {}", testName, screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot on test failure", e);
        }
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                WebDriverManager.getDriver(), 
                testName
            );
            
            if (screenshotPath != null) {
                logger.info("Screenshot captured for passed test: {} at {}", testName, screenshotPath);
            }
        } catch (Exception e) {
            logger.warn("Failed to capture screenshot on test success", e);
        }
    }
}
