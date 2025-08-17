package com.sri.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    
    static {
        createScreenshotDirectory();
    }
    
    private static void createScreenshotDirectory() {
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;
            
            File destFile = new File(filePath);
            FileUtils.copyFile(sourceFile, destFile);
            
            logger.info("Screenshot captured: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot for test: {}", testName, e);
            return null;
        }
    }
    
    public static String captureScreenshotOnFailure(WebDriver driver, String testName) {
        String screenshotPath = captureScreenshot(driver, testName + "_FAILED");
        if (screenshotPath != null) {
            logger.info("Failure screenshot captured for test: {}", testName);
        }
        return screenshotPath;
    }
    
    public static String getScreenshotPath(String fileName) {
        return new File(SCREENSHOT_DIR + fileName).getAbsolutePath();
    }
}
