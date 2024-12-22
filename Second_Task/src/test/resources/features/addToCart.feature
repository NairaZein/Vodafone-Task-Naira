Feature: Add items to cart in Vodafone eShop

  Scenario: Login and add items to cart
    Given I navigate to the Vodafone eShop login page
    When I enter valid credentials from the Excel sheet
    Then I should be logged in and redirected to the homepage
    And I select 2 items from the homepage and add to cart
    And I search for an item and add it to the cart
    Then I should see 3 items in the cart
