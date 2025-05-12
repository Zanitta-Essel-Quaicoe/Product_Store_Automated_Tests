package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By contactModal = By.id("exampleModal");
    private By contactForm = By.cssSelector("#exampleModal form");
    private By nameField = By.id("recipient-name");
    private By emailField = By.id("recipient-email");
    private By messageField = By.id("message-text");
    private By sendMessageButton = By.cssSelector("#exampleModal .btn-primary");

    public boolean isContactModalDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactModal));
        return driver.findElement(contactModal).isDisplayed();
    }

//    public boolean isContactFormDisplayed() {
//        return driver.findElement(contactForm).isDisplayed();
//    }

    public boolean isContactFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement contactModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));
        return contactModal.isDisplayed();
    }


    public void enterName(String name) {
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterMessage(String message) {
        driver.findElement(messageField).clear();
        driver.findElement(messageField).sendKeys(message);
    }

    public void clickSendMessage() {
        driver.findElement(sendMessageButton).click();
    }

    public boolean isNameFieldValid() {
        String nameValue = driver.findElement(nameField).getAttribute("value");
        return nameValue.matches("^[A-Za-z ]+$");
    }

    public boolean isEmailFieldValid() {
        String emailValue = driver.findElement(emailField).getAttribute("value");
        return emailValue.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public boolean isSubmissionConfirmed() {
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        return alertText.contains("Thanks for the message");
    }

    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return alertText;
    }
}
