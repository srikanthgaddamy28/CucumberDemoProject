package com.sri.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class FlipkartProductPage {
    private static final Logger logger = LogManager.getLogger(FlipkartProductPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//span[@class='B_NuCI']")
    private WebElement productTitle;
    
    @FindBy(xpath = "//div[@class='_30jeq3 _16Jk6d']")
    private WebElement productPrice;
    
    @FindBy(xpath = "//button[text()='ADD TO CART']")
    private WebElement addToCartButton;
    
    @FindBy(xpath = "//button[text()='BUY NOW']")
    private WebElement buyNowButton;
    
    @FindBy(xpath = "//div[contains(@class,'_16FRp0')]")
    private WebElement productDescription;
    
    @FindBy(xpath = "//div[text()='Added to cart']")
    private WebElement addedToCartConfirmation;
    
    @FindBy(xpath = "//div[@class='_3dsJAO _24d-qY FxZV4H']")
    private WebElement productRating;
    
    @FindBy(xpath = "//img[@class='_396cs4 _2amPTt _3qGmMb']")
    private WebElement productImage;
    
    public FlipkartProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public boolean isProductDetailsPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productTitle));
            boolean isDisplayed = productTitle.isDisplayed() && addToCartButton.isDisplayed();
            logger.info("Product details page displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Failed to verify product details page", e);
            return false;
        }
    }
    
    public boolean isProductInformationDisplayed() {
        try {
            boolean titleVisible = wait.until(ExpectedConditions.visibilityOf(productTitle)).isDisplayed();
            boolean priceVisible = productPrice.isDisplayed();
            boolean imageVisible = productImage.isDisplayed();
            
            boolean allInfoDisplayed = titleVisible && priceVisible && imageVisible;
            logger.info("Product information displayed - Title: {}, Price: {}, Image: {}", 
                       titleVisible, priceVisible, imageVisible);
            return allInfoDisplayed;
        } catch (Exception e) {
            logger.error("Failed to verify product information", e);
            return false;
        }
    }
    
    public void clickAddToCart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCartButton.click();
            logger.info("Clicked Add to Cart button");
        } catch (Exception e) {
            logger.error("Failed to click Add to Cart button", e);
            throw new RuntimeException("Add to Cart click failed", e);
        }
    }
    
    public boolean isProductAddedToCartConfirmationDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addedToCartConfirmation));
            boolean confirmationDisplayed = addedToCartConfirmation.isDisplayed();
            logger.info("Add to cart confirmation displayed: {}", confirmationDisplayed);
            return confirmationDisplayed;
        } catch (Exception e) {
            logger.error("Failed to verify add to cart confirmation", e);
            return false;
        }
    }
    
    public String getProductTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productTitle));
            String title = productTitle.getText();
            logger.info("Product title: {}", title);
            return title;
        } catch (Exception e) {
            logger.error("Failed to get product title", e);
            return "";
        }
    }
    
    public String getProductPrice() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productPrice));
            String price = productPrice.getText();
            logger.info("Product price: {}", price);
            return price;
        } catch (Exception e) {
            logger.error("Failed to get product price", e);
            return "";
        }
    }
}
