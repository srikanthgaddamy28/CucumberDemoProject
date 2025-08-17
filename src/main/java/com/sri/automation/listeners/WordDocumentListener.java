package com.sri.automation.listeners;

import com.sri.automation.utils.WordDocumentGenerator;
import com.sri.automation.utils.ScreenshotUtils;
import com.sri.automation.utils.WebDriverManager;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class WordDocumentListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(WordDocumentListener.class);
    private static final ConcurrentHashMap<String, WordDocumentGenerator> documentGenerators = new ConcurrentHashMap<>();
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WordDocumentGenerator generator = new WordDocumentGenerator(testName);
        documentGenerators.put(testName, generator);
        
        generator.addTestStep(
            "Test Initialization: " + testName,
            "PASS",
            null,
            "Test started successfully"
        );
        
        logger.info("Word document generator initialized for test: {}", testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WordDocumentGenerator generator = documentGenerators.get(testName);
        
        if (generator != null) {
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshot(
                    WebDriverManager.getDriver(), 
                    testName + "_SUCCESS"
                );
                
                generator.addTestStep(
                    "Test Execution Completed",
                    "PASS",
                    screenshotPath,
                    "All test steps executed successfully"
                );
                
                String documentPath = generator.saveDocument();
                logger.info("Word document generated for passed test: {} at {}", testName, documentPath);
                
            } catch (Exception e) {
                logger.error("Failed to generate Word document for passed test: {}", testName, e);
            } finally {
                documentGenerators.remove(testName);
            }
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WordDocumentGenerator generator = documentGenerators.get(testName);
        
        if (generator != null) {
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
                    WebDriverManager.getDriver(), 
                    testName
                );
                
                generator.addTestStep(
                    "Test Execution Failed",
                    "FAIL",
                    screenshotPath,
                    "Error: " + result.getThrowable().getMessage()
                );
                
                String documentPath = generator.saveDocument();
                logger.info("Word document generated for failed test: {} at {}", testName, documentPath);
                
            } catch (Exception e) {
                logger.error("Failed to generate Word document for failed test: {}", testName, e);
            } finally {
                documentGenerators.remove(testName);
            }
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WordDocumentGenerator generator = documentGenerators.get(testName);
        
        if (generator != null) {
            generator.addTestStep(
                "Test Execution Skipped",
                "SKIP",
                null,
                "Reason: " + (result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown")
            );
            
            String documentPath = generator.saveDocument();
            logger.info("Word document generated for skipped test: {} at {}", testName, documentPath);
            documentGenerators.remove(testName);
        }
    }
    
    public static void addStepToDocument(String testName, String stepDescription, String status, String additionalInfo) {
        WordDocumentGenerator generator = documentGenerators.get(testName);
        if (generator != null) {
            try {
                String screenshotPath = null;
                if (WebDriverManager.getDriver() != null) {
                    screenshotPath = ScreenshotUtils.captureScreenshot(
                        WebDriverManager.getDriver(), 
                        testName + "_" + stepDescription.replaceAll("[^a-zA-Z0-9]", "_")
                    );
                }
                
                generator.addTestStep(stepDescription, status, screenshotPath, additionalInfo);
                logger.debug("Step added to Word document for test {}: {}", testName, stepDescription);
                
            } catch (Exception e) {
                logger.error("Failed to add step to Word document for test: {}", testName, e);
            }
        }
    }
}
