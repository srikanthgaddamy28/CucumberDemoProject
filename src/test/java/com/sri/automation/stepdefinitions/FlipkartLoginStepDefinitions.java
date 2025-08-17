package com.sri.automation.stepdefinitions;

import com.sri.automation.utils.WebDriverManager;
import com.sri.automation.listeners.WordDocumentListener;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class FlipkartLoginStepDefinitions {
    private static final Logger logger = LogManager.getLogger(FlipkartLoginStepDefinitions.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private String currentTestName = "FlipkartLoginTest";
    
    @FindBy(xpath = "//div[contains(@class,'_2IX_2-')]")
    private WebElement loginModal;
    
    @FindBy(xpath = "//input[@class='_2IX_2- VJZDxU']")
    private WebElement emailField;
    
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[@class='_2KpZ6l _2HKlqd _3AWRsL']")
    private WebElement loginSubmitButton;
    
    @FindBy(xpath = "//span[contains(text(),'Please enter valid Email ID/Mobile number')]")
    private WebElement loginErrorMessage;
    
    public FlipkartLoginStepDefinitions() {
        this.driver = WebDriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    @Then("I should see the login modal")
    public void i_should_see_the_login_modal() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginModal));
            Assert.assertTrue(loginModal.isDisplayed(), "Login modal not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Login Modal", "PASS", 
                "Login modal displayed successfully");
            
            logger.info("Login modal verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Login Modal", "FAIL", 
                "Login modal verification failed: " + e.getMessage());
            logger.error("Failed to verify login modal", e);
            throw e;
        }
    }
    
    @When("I enter email {string}")
    public void i_enter_email(String email) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(emailField));
            emailField.clear();
            emailField.sendKeys(email);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Enter Email: " + email, "PASS", 
                "Successfully entered email address");
            
            logger.info("Successfully entered email: {}", email);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Enter Email: " + email, "FAIL", 
                "Failed to enter email: " + e.getMessage());
            logger.error("Failed to enter email: {}", email, e);
            throw e;
        }
    }
    
    @And("I enter password {string}")
    public void i_enter_password(String password) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            passwordField.clear();
            passwordField.sendKeys(password);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Enter Password", "PASS", 
                "Successfully entered password");
            
            logger.info("Successfully entered password");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Enter Password", "FAIL", 
                "Failed to enter password: " + e.getMessage());
            logger.error("Failed to enter password", e);
            throw e;
        }
    }
    
    @And("I click on {string} submit button")
    public void i_click_on_submit_button(String buttonType) {
        try {
            if ("Login".equalsIgnoreCase(buttonType)) {
                wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton));
                loginSubmitButton.click();
            }
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + buttonType + " Submit Button", "PASS", 
                "Successfully clicked on " + buttonType + " submit button");
            
            logger.info("Successfully clicked on {} submit button", buttonType);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + buttonType + " Submit Button", "FAIL", 
                "Failed to click submit button: " + e.getMessage());
            logger.error("Failed to click {} submit button", buttonType, e);
            throw e;
        }
    }
    
    @Then("I should see login error message for invalid credentials")
    public void i_should_see_login_error_message_for_invalid_credentials() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));
            Assert.assertTrue(loginErrorMessage.isDisplayed(), "Login error message not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Login Error Message", "PASS", 
                "Login error message displayed for invalid credentials");
            
            logger.info("Login error message verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Login Error Message", "FAIL", 
                "Login error message verification failed: " + e.getMessage());
            logger.error("Failed to verify login error message", e);
            throw e;
        }
    }
}
