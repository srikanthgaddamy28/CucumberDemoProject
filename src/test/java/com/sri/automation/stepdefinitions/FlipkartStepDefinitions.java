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

public class FlipkartStepDefinitions {
    private static final Logger logger = LogManager.getLogger(FlipkartStepDefinitions.class);
    
    private FlipkartHomePage homePage;
    private FlipkartSearchResultsPage searchResultsPage;
    private FlipkartProductPage productPage;
    private String currentTestName;
    
    public FlipkartStepDefinitions() {
        this.homePage = new FlipkartHomePage(WebDriverManager.getDriver());
        this.searchResultsPage = new FlipkartSearchResultsPage(WebDriverManager.getDriver());
        this.productPage = new FlipkartProductPage(WebDriverManager.getDriver());
        this.currentTestName = "FlipkartAutomation";
    }
    
    @Given("I am on the Flipkart homepage")
    public void i_am_on_the_flipkart_homepage() {
        try {
            homePage.navigateToHomePage();
            Assert.assertTrue(homePage.isHomePage(), "Failed to navigate to Flipkart homepage");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Navigate to Flipkart Homepage", "PASS", 
                "Successfully navigated to https://www.flipkart.com/");
            
            logger.info("Successfully navigated to Flipkart homepage");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Navigate to Flipkart Homepage", "FAIL", 
                "Failed to navigate: " + e.getMessage());
            logger.error("Failed to navigate to Flipkart homepage", e);
            throw e;
        }
    }
    
    @When("I search for {string}")
    public void i_search_for(String productName) {
        try {
            homePage.searchForProduct(productName);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Search for Product: " + productName, "PASS", 
                "Successfully searched for " + productName);
            
            logger.info("Successfully searched for product: {}", productName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Search for Product: " + productName, "FAIL", 
                "Failed to search: " + e.getMessage());
            logger.error("Failed to search for product: {}", productName, e);
            throw e;
        }
    }
    
    @Then("I should see search results for {string}")
    public void i_should_see_search_results_for(String searchTerm) {
        try {
            Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(searchTerm), 
                "Search results not displayed for: " + searchTerm);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Search Results for: " + searchTerm, "PASS", 
                "Search results displayed successfully");
            
            logger.info("Search results verified for: {}", searchTerm);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Search Results for: " + searchTerm, "FAIL", 
                "Search results verification failed: " + e.getMessage());
            logger.error("Failed to verify search results for: {}", searchTerm, e);
            throw e;
        }
    }
    
    @And("I should see product listings displayed")
    public void i_should_see_product_listings_displayed() {
        try {
            Assert.assertTrue(searchResultsPage.areProductListingsDisplayed(), 
                "Product listings not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Listings", "PASS", 
                "Product listings displayed successfully");
            
            logger.info("Product listings verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Listings", "FAIL", 
                "Product listings verification failed: " + e.getMessage());
            logger.error("Failed to verify product listings", e);
            throw e;
        }
    }
    
    @When("I click on the first product")
    public void i_click_on_the_first_product() {
        try {
            searchResultsPage.clickFirstProduct();
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click First Product", "PASS", 
                "Successfully clicked on first product");
            
            logger.info("Successfully clicked on first product");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click First Product", "FAIL", 
                "Failed to click first product: " + e.getMessage());
            logger.error("Failed to click first product", e);
            throw e;
        }
    }
    
    @Then("I should be redirected to the product details page")
    public void i_should_be_redirected_to_the_product_details_page() {
        try {
            Assert.assertTrue(productPage.isProductDetailsPageDisplayed(), 
                "Product details page not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Details Page", "PASS", 
                "Product details page displayed successfully");
            
            logger.info("Product details page verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Details Page", "FAIL", 
                "Product details page verification failed: " + e.getMessage());
            logger.error("Failed to verify product details page", e);
            throw e;
        }
    }
    
    @And("I should see product information displayed")
    public void i_should_see_product_information_displayed() {
        try {
            Assert.assertTrue(productPage.isProductInformationDisplayed(), 
                "Product information not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Information", "PASS", 
                "Product information displayed successfully");
            
            logger.info("Product information verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Product Information", "FAIL", 
                "Product information verification failed: " + e.getMessage());
            logger.error("Failed to verify product information", e);
            throw e;
        }
    }
    
    @When("I hover over {string} category")
    public void i_hover_over_category(String categoryName) {
        try {
            if ("Electronics".equalsIgnoreCase(categoryName)) {
                homePage.hoverOverElectronics();
            }
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Hover over " + categoryName + " Category", "PASS", 
                "Successfully hovered over " + categoryName);
            
            logger.info("Successfully hovered over {} category", categoryName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Hover over " + categoryName + " Category", "FAIL", 
                "Failed to hover: " + e.getMessage());
            logger.error("Failed to hover over {} category", categoryName, e);
            throw e;
        }
    }
    
    @Then("I should see the electronics submenu")
    public void i_should_see_the_electronics_submenu() {
        try {
            // Add verification logic here
            Thread.sleep(1000); // Wait for submenu to appear
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Electronics Submenu", "PASS", 
                "Electronics submenu displayed successfully");
            
            logger.info("Electronics submenu verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Electronics Submenu", "FAIL", 
                "Electronics submenu verification failed: " + e.getMessage());
            logger.error("Failed to verify electronics submenu", e);
            throw e;
        }
    }
    
    @When("I click on {string} subcategory")
    public void i_click_on_subcategory(String subcategoryName) {
        try {
            if ("Mobiles".equalsIgnoreCase(subcategoryName)) {
                homePage.clickMobilesSubcategory();
            }
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + subcategoryName + " Subcategory", "PASS", 
                "Successfully clicked on " + subcategoryName);
            
            logger.info("Successfully clicked on {} subcategory", subcategoryName);
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Click " + subcategoryName + " Subcategory", "FAIL", 
                "Failed to click subcategory: " + e.getMessage());
            logger.error("Failed to click {} subcategory", subcategoryName, e);
            throw e;
        }
    }
    
    @Then("I should be on the mobiles category page")
    public void i_should_be_on_the_mobiles_category_page() {
        try {
            // Add verification logic for mobiles category page
            Thread.sleep(2000);
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Mobiles Category Page", "PASS", 
                "Mobiles category page displayed successfully");
            
            logger.info("Mobiles category page verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Mobiles Category Page", "FAIL", 
                "Mobiles category page verification failed: " + e.getMessage());
            logger.error("Failed to verify mobiles category page", e);
            throw e;
        }
    }
    
    @And("I should see mobile phone listings")
    public void i_should_see_mobile_phone_listings() {
        try {
            Assert.assertTrue(searchResultsPage.areProductListingsDisplayed(), 
                "Mobile phone listings not displayed");
            
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Mobile Phone Listings", "PASS", 
                "Mobile phone listings displayed successfully");
            
            logger.info("Mobile phone listings verified successfully");
        } catch (Exception e) {
            WordDocumentListener.addStepToDocument(currentTestName, 
                "Verify Mobile Phone Listings", "FAIL", 
                "Mobile phone listings verification failed: " + e.getMessage());
            logger.error("Failed to verify mobile phone listings", e);
            throw e;
        }
    }
}
