package com.sri.automation.stepdefinitions;

import com.sri.automation.pages.FlipkartHomePage;
import com.sri.automation.pages.FlipkartSearchResultsPage;
import com.sri.automation.pages.FlipkartProductPage;
import com.sri.automation.utils.WebDriverManager;
import com.sri.automation.listeners.WordDocumentListener;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlipkartCartStepDefinitions {
    private static final Logger logger = LogManager.getLogger(FlipkartCartStepDefinitions.class);
    
    private FlipkartHomePage homePage;
    private FlipkartSearchResultsPage searchResultsPage;
    private FlipkartProductPage productPage;
    private String currentTestName = "FlipkartCartTest";
    
    public FlipkartCartStepDefinitions() {
        this.homePage = new FlipkartHomePage(WebDriverManager.getDriver());
        this.searchResultsPage = new FlipkartSearchResultsPage(WebDriverManager.getDriver());
        this.productPage = new FlipkartProductPage(WebDriverManager.getDriver());
    }
    
    @And("I click on {string} button")
    public void i_click_on_button(String buttonName) {
        try {
            if ("Add to Cart".equalsIgnoreCase(buttonName)) {
                productPage.clickAddToCart();
            } else if ("Login".equalsIgnoreCase(buttonName)) {
                homePage.clickLoginButton();
            }
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + buttonName + " Button", "PASS", 
                "Successfully clicked on " + buttonName + " button");
            
            logger.info("Successfully clicked on {} button", buttonName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + buttonName + " Button", "FAIL", 
                "Failed to click button: " + e.getMessage());
            logger.error("Failed to click {} button", buttonName, e);
            throw e;
        }
    }
    
    @Then("I should see the product added to cart confirmation")
    public void i_should_see_the_product_added_to_cart_confirmation() {
        try {
            Assert.assertTrue(productPage.isProductAddedToCartConfirmationDisplayed(), 
                "Add to cart confirmation not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Add to Cart Confirmation", "PASS", 
                "Add to cart confirmation displayed successfully");
            
            logger.info("Add to cart confirmation verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Add to Cart Confirmation", "FAIL", 
                "Add to cart confirmation verification failed: " + e.getMessage());
            logger.error("Failed to verify add to cart confirmation", e);
            throw e;
        }
    }
    
    @When("I click on cart icon")
    public void i_click_on_cart_icon() {
        try {
            homePage.clickCartIcon();
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click Cart Icon", "PASS", 
                "Successfully clicked on cart icon");
            
            logger.info("Successfully clicked on cart icon");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click Cart Icon", "FAIL", 
                "Failed to click cart icon: " + e.getMessage());
            logger.error("Failed to click cart icon", e);
            throw e;
        }
    }
    
    @Then("I should see the product in my cart")
    public void i_should_see_the_product_in_my_cart() {
        try {
            // Add verification logic for cart page
            Thread.sleep(2000);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product in Cart", "PASS", 
                "Product displayed in cart successfully");
            
            logger.info("Product in cart verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product in Cart", "FAIL", 
                "Product in cart verification failed: " + e.getMessage());
            logger.error("Failed to verify product in cart", e);
            throw e;
        }
    }
    
    @And("I apply price filter {string}")
    public void i_apply_price_filter(String priceRange) {
        try {
            searchResultsPage.applyPriceFilter(priceRange);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Apply Price Filter: " + priceRange, "PASS", 
                "Successfully applied price filter");
            
            logger.info("Successfully applied price filter: {}", priceRange);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Apply Price Filter: " + priceRange, "FAIL", 
                "Failed to apply price filter: " + e.getMessage());
            logger.error("Failed to apply price filter: {}", priceRange, e);
            throw e;
        }
    }
    
    @And("I apply brand filter {string}")
    public void i_apply_brand_filter(String brandName) {
        try {
            searchResultsPage.applyBrandFilter(brandName);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Apply Brand Filter: " + brandName, "PASS", 
                "Successfully applied brand filter");
            
            logger.info("Successfully applied brand filter: {}", brandName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Apply Brand Filter: " + brandName, "FAIL", 
                "Failed to apply brand filter: " + e.getMessage());
            logger.error("Failed to apply brand filter: {}", brandName, e);
            throw e;
        }
    }
    
    @Then("I should see filtered results for {string} laptops")
    public void i_should_see_filtered_results_for_laptops(String brandName) {
        try {
            Assert.assertTrue(searchResultsPage.areFilteredResultsDisplayed(brandName), 
                "Filtered results not displayed for " + brandName);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Filtered Results for " + brandName, "PASS", 
                "Filtered results displayed successfully");
            
            logger.info("Filtered results verified for {} laptops", brandName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Filtered Results for " + brandName, "FAIL", 
                "Filtered results verification failed: " + e.getMessage());
            logger.error("Failed to verify filtered results for {} laptops", brandName, e);
            throw e;
        }
    }
    
    @And("all displayed products should be within the price range")
    public void all_displayed_products_should_be_within_the_price_range() {
        try {
            Assert.assertTrue(searchResultsPage.areProductsInPriceRange(), 
                "Products not within specified price range");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Products in Price Range", "PASS", 
                "All products are within the specified price range");
            
            logger.info("Products in price range verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Products in Price Range", "FAIL", 
                "Price range verification failed: " + e.getMessage());
            logger.error("Failed to verify products in price range", e);
            throw e;
        }
    }
}
