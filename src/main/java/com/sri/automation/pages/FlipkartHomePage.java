package com.sri.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class FlipkartHomePage {
    private static final Logger logger = LogManager.getLogger(FlipkartHomePage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    
    @FindBy(xpath = "//input[@name='q']")
    private WebElement searchBox;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;
    
    @FindBy(xpath = "//a[contains(text(),'Login')]")
    private WebElement loginButton;
    
    @FindBy(xpath = "//span[text()='Electronics']")
    private WebElement electronicsCategory;
    
    @FindBy(xpath = "//a[text()='Mobiles']")
    private WebElement mobilesSubcategory;
    
    @FindBy(xpath = "//a[contains(@class,'_1krdK5')]")
    private WebElement cartIcon;
    
    @FindBy(xpath = "//button[text()='✕']")
    private WebElement closeLoginModal;
    
    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToHomePage() {
        try {
            driver.get("https://www.flipkart.com/");
            logger.info("Navigated to Flipkart homepage");
            
            // Close login modal if it appears
            try {
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeLoginModal));
                closeButton.click();
                logger.info("Closed login modal");
            } catch (Exception e) {
                logger.debug("Login modal not present or already closed");
            }
        } catch (Exception e) {
            logger.error("Failed to navigate to Flipkart homepage", e);
            throw new RuntimeException("Navigation to homepage failed", e);
        }
    }
    
    public void searchForProduct(String productName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            searchBox.clear();
            searchBox.sendKeys(productName);
            searchButton.click();
            logger.info("Searched for product: {}", productName);
        } catch (Exception e) {
            logger.error("Failed to search for product: {}", productName, e);
            throw new RuntimeException("Product search failed", e);
        }
    }
    
    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
            logger.info("Clicked on login button");
        } catch (Exception e) {
            logger.error("Failed to click login button", e);
            throw new RuntimeException("Login button click failed", e);
        }
    }
    
    public void hoverOverElectronics() {
        try {
            wait.until(ExpectedConditions.visibilityOf(electronicsCategory));
            actions.moveToElement(electronicsCategory).perform();
            logger.info("Hovered over Electronics category");
        } catch (Exception e) {
            logger.error("Failed to hover over Electronics category", e);
            throw new RuntimeException("Electronics hover failed", e);
        }
    }
    
    public void clickMobilesSubcategory() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(mobilesSubcategory));
            mobilesSubcategory.click();
            logger.info("Clicked on Mobiles subcategory");
        } catch (Exception e) {
            logger.error("Failed to click Mobiles subcategory", e);
            throw new RuntimeException("Mobiles subcategory click failed", e);
        }
    }
    
    public void clickCartIcon() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
            cartIcon.click();
            logger.info("Clicked on cart icon");
        } catch (Exception e) {
            logger.error("Failed to click cart icon", e);
            throw new RuntimeException("Cart icon click failed", e);
        }
    }
    
    public boolean isHomePage() {
        try {
            return driver.getCurrentUrl().contains("flipkart.com") && 
                   wait.until(ExpectedConditions.visibilityOf(searchBox)).isDisplayed();
        } catch (Exception e) {
            logger.error("Failed to verify homepage", e);
            return false;
        }
    }
}
