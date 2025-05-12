package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PlaceOrderPage {
    private WebDriver driver;

    // Locators for order form fields
    private final By nameField = By.id("name");
    private final By addressField = By.id("address");
    private final By cityField = By.id("city");
    private final By countryField = By.id("country");
    private final By creditCardField = By.id("card");
    private final By monthField = By.id("month");
    private final By yearField = By.id("year");
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");

    // Locators for error messages
    private final By nameError = By.id("name-error");
    private final By addressError = By.id("address-error");
    private final By creditCardError = By.id("card-error");

    // Confirmation message and redirection locators
    private final By confirmationMessage = By.id("order-confirmation");
    private final By homePageLink = By.id("home-link");

    private final By modalPlaceOrderButton = By.cssSelector("#orderModal .modal-footer button.btn.btn-primary");

    public PlaceOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

//    public void enterAddress(String address) {
//        driver.findElement(addressField).sendKeys(address);
//    }

    public void enterCity(String city) {
        driver.findElement(cityField).sendKeys(city);
    }

    public void enterCountry(String country) {
        driver.findElement(countryField).sendKeys(country);
    }

    public void enterCreditCard(String creditCard) {
        driver.findElement(creditCardField).sendKeys(creditCard);
    }

    public void enterMonth(String month) {
        driver.findElement(monthField).sendKeys(month);
    }

    public void enterYear(String year) {
        driver.findElement(yearField).sendKeys(year);
    }

    public void placeOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
        WebElement placeOrderButtonElement = driver.findElement(modalPlaceOrderButton);
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButtonElement)).click();
    }



    public void addToCart() {
        driver.findElement(By.linkText("Add to cart")).click();
    }

    public boolean isNameErrorDisplayed() {
        return driver.findElement(nameError).isDisplayed();
    }

    public boolean isAddressErrorDisplayed() {
        return driver.findElement(addressError).isDisplayed();
    }

    public boolean isCreditCardErrorDisplayed() {
        return driver.findElement(creditCardError).isDisplayed();
    }

    public boolean isConfirmationMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
        return message.getText().contains("Thank you for your purchase!");
    }


    private final By confirmationModal = By.cssSelector(".sweet-alert h2");

    private final By orderFormModal = By.id("orderModal");

    public boolean isOrderFormDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderFormModal));
            return driver.findElement(orderFormModal).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isNameFieldValid() {
        String name = driver.findElement(nameField).getAttribute("value");
        return name.matches("^[a-zA-Z\\s]+$");
    }

    public boolean isCreditCardFieldValid() {
        String cardNumber = driver.findElement(creditCardField).getAttribute("value");
        return cardNumber.matches("^\\d{16}$");
    }

    public String getAlertTextAndAccept() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        driver.switchTo().alert().accept();
        return alertText;
    }




}
