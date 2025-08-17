package com.sri.automation.stepdefinitions;

import com.sri.automation.utils.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    
    @Before
    public void setUp(Scenario scenario) {
        try {
            String browserName = System.getProperty("browser", "chrome");
            WebDriverManager.initializeDriver(browserName);
            logger.info("Test setup completed for scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Failed to setup test for scenario: {}", scenario.getName(), e);
            throw new RuntimeException("Test setup failed", e);
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.error("Scenario failed: {}", scenario.getName());
            } else {
                logger.info("Scenario passed: {}", scenario.getName());
            }
            
            WebDriverManager.quitDriver();
            logger.info("Test teardown completed for scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Failed to teardown test for scenario: {}", scenario.getName(), e);
        }
    }
}
