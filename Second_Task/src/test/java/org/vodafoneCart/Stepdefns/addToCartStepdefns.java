package org.vodafoneCart.Stepdefns;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.io.File;
import java.util.List;

public class addToCartStepdefns {

    WebDriver driver;

    @Given("I navigate to the Vodafone eShop login page")

    public void navigateToLoginPage() {

        driver = new ChromeDriver();
        driver.get("https://eshop.vodafone.com.eg/shop/home");
        driver.manage().window().maximize();


        if (driver.getTitle().contains("Page Not Found")) {
            driver.findElement(By.id("home-button-1")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='onetrust-close-btn-container']/button")));
            closeButton.click();
            System.out.println("Cookie popup closed");
        } catch (Exception e) {
            System.out.println("No cookie popup found, continuing");
        }
    }
    }

    @When("I enter valid credentials from the Excel sheet")
    public void enterValidCredentialsFromExcel() throws IOException {
        driver.findElement(By.xpath("//*[@id=\"userProfileMenu\"]/button/img")).click();
        FileInputStream file = new FileInputStream(new File("src/test/resources/TestData.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        String phoneNumber;
        String password;

        if (row.getCell(0).getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
            phoneNumber = String.valueOf((long) row.getCell(0).getNumericCellValue());
        } else {
            phoneNumber = row.getCell(0).getStringCellValue();
        }

        password = row.getCell(1).getStringCellValue();

        driver.findElement(By.id("username")).sendKeys(phoneNumber);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();

          workbook.close();
         file.close();
    }


    @Then("I should be logged in and redirected to the homepage")
    public void verifyLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("https://eshop.vodafone.com.eg/en/"), "The URL does not contain the expected path");
    }


    @When("I select 2 items from the homepage and add to cart")
    public void addItemsFromHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/vf-root/main/section[2]/vf-landing/section/section/vf-landing-brands/div/div/div[2]")));
        element.click();
        WebElement productCards = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-cards")));

        List<WebElement> items = productCards.findElements(By.xpath("/html/body/vf-root/main/section[2]/vf-product-list-page/div/div/div[2]/div[5]/vf-product-box-featured[1]"));

        if (!items.isEmpty()) {
            items.get(0).click();
            WebElement clickCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-to-cart")));
            clickCartBtn.click();
            WebElement alertBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/vf-root/main/section[1]/vf-alert-box/div")));
            String alertMessage = alertBox.getText();
            String expectedMessage = "Item Added To Cart Successfully!";
            Assert.assertEquals(alertMessage, expectedMessage, "Alert message is not as expected!");
            System.out.println("Alert message: " + alertMessage);
        } else {
            System.out.println("No products found.");
        }
        WebElement homeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("logo")));
        homeButton.click();

        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/vf-root/main/section[2]/vf-landing/section/section/vf-landing-brands/div/div/div[1]")));
        element2.click();
        WebElement productCards2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-cards")));
        List<WebElement> items2 = productCards2.findElements(By.className("product-card-container"));

        if (!items2.isEmpty() && items2.size() > 1) {
            items2.get(1).click();
            WebElement clickCartBtn2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-to-cart")));
            clickCartBtn2.click();

            WebElement alertBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/vf-root/main/section[1]/vf-alert-box/div")));
            String alertMessage2 = alertBox2.getText();
            String expectedMessage = "Item Added To Cart Successfully!";
            Assert.assertEquals(alertMessage2, expectedMessage, "Alert message is not as expected!");
            System.out.println("Alert message: " + alertMessage2);
        } else {
            System.out.println("No products found for the second selection.");
        }
    }

    @Then("I search for an item and add it to the cart")
        public void searchForItemAndAddToCart() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
            searchBar.sendKeys("hp");
            searchBar.sendKeys(Keys.ENTER);

        WebElement SearchproductCards = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-cards")));
        List<WebElement> item3 = SearchproductCards.findElements(By.xpath("/html/body/vf-root/main/section[2]/vf-product-list-page/div/div/div[2]/div[5]/vf-product-box-featured[1]"));

            if (!item3.isEmpty()) {
                item3.get(0).click();

                WebElement clickCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-to-cart")));
                clickCartBtn.click();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WebElement alertBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/vf-root/main/section[1]/vf-alert-box/div")));
                String alertMessage = alertBox.getText();
                String expectedMessage = "Item Added To Cart Successfully!";
               Assert.assertEquals(alertMessage, expectedMessage, "Alert message is not as expected!");
                System.out.println("Alert message: " + alertMessage);
            } else {
                System.out.println("No search results found for 'hp'");
            }
        }


    @Then("I should see 3 items in the cart")
    public void verifyCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.navigate().refresh();
        WebElement cartCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-btn"))); // Replace with the actual class name for the cart count
        cartCountElement.click();
        int displayedCount = Integer.parseInt(cartCountElement.getText());
        int expectedCount = 3;
        Assert.assertEquals(displayedCount, expectedCount, "Cart does not contain 3 items!");

        System.out.println("Cart contains " + displayedCount + " items as expected.");
    }
    @After
    public void tearDown() {

            driver.quit();

    }

}