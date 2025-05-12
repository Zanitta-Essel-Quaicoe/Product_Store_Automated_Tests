package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By addToCartBtn = By.cssSelector(".btn.btn-success.btn-lg");
    private final By productTitle = By.cssSelector("h2.name");
    private final By productPrice = By.cssSelector("h3.price-container");
    private final By productDescription = By.cssSelector("p#description");
    private final By productCategory = By.cssSelector("div#category");
    private final By productModal = By.cssSelector(".modal-body");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method to click on a product by name
    public void clickProduct(String productName) {
        driver.findElement(By.linkText(productName)).click();
    }

    // Method to add a product to the cart and accept the alert
    public void addToCartAndAcceptAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        driver.findElement(addToCartBtn).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }


    // Get the product title from the product page
    public String getProductTitle() {
        return driver.findElement(productTitle).getText();
    }

    // Get the product price from the product page
    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    // Get the product description from the product page
    public String getProductDescription() {
        return driver.findElement(productDescription).getText();
    }

    // Get the product category from the product page
    public String getProductCategory() {
        return driver.findElement(productCategory).getText();
    }

    // Verify if the product modal is visible
    public boolean isProductModalVisible() {
        return driver.findElement(productModal).isDisplayed();
    }
}
