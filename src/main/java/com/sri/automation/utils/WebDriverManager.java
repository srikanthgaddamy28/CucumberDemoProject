package com.sri.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class WebDriverManager {
    private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void initializeDriver(String browserName) {
        try {
            WebDriver webDriver = null;
            
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                    
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions headlessOptions = new ChromeOptions();
                    headlessOptions.addArguments("--headless");
                    headlessOptions.addArguments("--no-sandbox");
                    headlessOptions.addArguments("--disable-dev-shm-usage");
                    headlessOptions.addArguments("--window-size=1920,1080");
                    webDriver = new ChromeDriver(headlessOptions);
                    break;
                    
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    webDriver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    webDriver = new EdgeDriver(edgeOptions);
                    break;
                    
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }
            
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.set(webDriver);
            
            logger.info("WebDriver initialized successfully for browser: {}", browserName);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: {}", browserName, e);
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driver.remove();
            }
        }
    }
}
