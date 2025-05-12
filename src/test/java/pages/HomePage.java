package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    // Locators
    private final By carousel = By.id("carouselExampleIndicators");
    private final By carouselIndicators = By.cssSelector(".carousel-indicators > li");
    private final By carouselNextButton = By.cssSelector(".carousel-control-next");
    private final By carouselPrevButton = By.cssSelector(".carousel-control-prev");
    private final By navbar = By.className("navbar");
    private final By productCards = By.cssSelector(".card.h-100");
    private final By productCategories = By.cssSelector("#itemc");
    private final By productTitles = By.cssSelector(".card-title");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.demoblaze.com/index.html");
    }

    public boolean isCarouselDisplayed() {
        return driver.findElement(carousel).isDisplayed();
    }

    public int getCarouselIndicatorsCount() {
        return driver.findElements(carouselIndicators).size();
    }

    public void clickNextCarousel() {
        driver.findElement(carouselNextButton).click();
    }

    public void clickPrevCarousel() {
        driver.findElement(carouselPrevButton).click();
    }

    public boolean isNavbarDisplayed() {
        return driver.findElement(navbar).isDisplayed();
    }

    public List<WebElement> getProductCards() {
        return driver.findElements(productCards);
    }

    public List<WebElement> getProductTitles() {
        return driver.findElements(productTitles);
    }

    public void clickFirstProduct() {
        driver.findElements(productTitles).get(0).click();
    }

    public List<WebElement> getProductCategories() {
        return driver.findElements(productCategories);
    }

    public void clickCategoryByName(String name) {
        for (WebElement category : getProductCategories()) {
            if (category.getText().equalsIgnoreCase(name)) {
                category.click();
                break;
            }
        }
    }

    public void clickProduct(String productName) {
        System.out.println("Attempting to click product: " + productName);

        List<WebElement> productElements = driver.findElements(By.cssSelector(".card-title a"));
        for (WebElement el : productElements) {
            String text = el.getText().trim();
            System.out.println("Available product: '" + text + "'");
            if (text.equalsIgnoreCase(productName.trim())) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(el))
                        .click();
                return;
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }


    public void clickContactLink() {
        driver.findElement(By.xpath("//a[text()='Contact']")).click();
    }




}
