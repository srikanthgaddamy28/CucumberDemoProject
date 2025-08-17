package com.sri.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sri.automation.utils.ScreenshotUtils;
import com.sri.automation.utils.WebDriverManager;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    
    static {
        setupExtentReports();
    }
    
    private static void setupExtentReports() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath = "test-output/extent-reports/FlipkartAutomation_" + timestamp + ".html";
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Flipkart Automation Test Report");
        sparkReporter.config().setReportName("SriAI Demo Project");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", System.getProperty("environment", "Local"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "Chrome"));
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getTestClass().getName());
        extentTest.set(test);
        logger.info("Test started: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.PASS, "Test passed successfully");
        
        // Capture screenshot on success
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                WebDriverManager.getDriver(), 
                result.getMethod().getMethodName()
            );
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            logger.warn("Could not capture screenshot on test success", e);
        }
        
        logger.info("Test passed: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
                WebDriverManager.getDriver(), 
                result.getMethod().getMethodName()
            );
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            logger.warn("Could not capture screenshot on test failure", e);
        }
        
        logger.error("Test failed: {}", result.getMethod().getMethodName(), result.getThrowable());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.SKIP, "Test skipped: " + result.getThrowable().getMessage());
        logger.info("Test skipped: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onFinish(org.testng.ITestContext context) {
        if (extent != null) {
            extent.flush();
            logger.info("Extent report generated successfully");
        }
    }
    
    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }
}
