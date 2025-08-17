Feature: Flipkart Product Search and Navigation
  As a user of Flipkart
  I want to search for products and navigate through the website
  So that I can find and view products I'm interested in

  Background:
    Given I am on the Flipkart homepage

  @smoke @search
  Scenario: Search for a mobile phone
    When I search for "iPhone 15"
    Then I should see search results for "iPhone 15"
    And I should see product listings displayed
    When I click on the first product
    Then I should be redirected to the product details page
    And I should see product information displayed

  @smoke @navigation
  Scenario: Navigate through product categories
    When I hover over "Electronics" category
    Then I should see the electronics submenu
    When I click on "Mobiles" subcategory
    Then I should be on the mobiles category page
    And I should see mobile phone listings

  @regression @cart
  Scenario: Add product to cart
    When I search for "Samsung Galaxy S24"
    And I click on the first product
    And I click on "Add to Cart" button
    Then I should see the product added to cart confirmation
    When I click on cart icon
    Then I should see the product in my cart

  @regression @login
  Scenario: User login functionality
    When I click on "Login" button
    Then I should see the login modal
    When I enter email "test@example.com"
    And I enter password "testpassword"
    And I click on "Login" submit button
    Then I should see login error message for invalid credentials

  @smoke @filters
  Scenario: Apply product filters
    When I search for "laptop"
    And I apply price filter "₹20,000 - ₹40,000"
    And I apply brand filter "HP"
    Then I should see filtered results for HP laptops
    And all displayed products should be within the price range
