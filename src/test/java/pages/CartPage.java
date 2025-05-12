package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By cartTable = By.id("tbodyid");
    private By totalPrice = By.id("totalp");
    private By deleteButtons = By.xpath("//a[text()='Delete']");
    private By placeOrderButton = By.xpath("//button[text()='Place Order']");
    private By cartNavLink = By.id("cartur");
    private By emptyCartMessage = By.id("empty-cart-message");


    public void navigateToCart() {
        driver.findElement(cartNavLink).click();
    }

    public boolean isProductInCart(String productName) {
        return driver.findElements(By.xpath("//td[text()='" + productName + "']")).size() > 0;
    }

    public int getProductCount() {
        return driver.findElements(By.xpath("//tr")).size();
    }

    public int getTotalCost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(driver -> {
                WebElement totalElement = driver.findElement(totalPrice);
                String text = totalElement.getText().trim();
                return !text.isEmpty();
            });

            String price = driver.findElement(totalPrice).getText().trim();
            return Integer.parseInt(price);
        } catch (Exception e) {
            // If element is empty or missing, return 0 as fallback
            return 0;
        }
    }


    public long getProductRowCount(String productName) {
        List<WebElement> nameCells = driver.findElements(By.xpath("//tr/td[2]"));
        return nameCells.stream()
                .map(WebElement::getText)
                .filter(name -> name.equalsIgnoreCase(productName))
                .count();
    }


    public List<String> getAllProductNames() {
        List<WebElement> nameCells = driver.findElements(By.xpath("//tr/td[2]")); // td[2] usually has product name
        return nameCells.stream().map(WebElement::getText).toList();
    }

    public void deleteFirstProduct() {
        List<WebElement> deleteBtns = driver.findElements(deleteButtons);
        if (!deleteBtns.isEmpty()) {
            int initialCount = getProductCount();
            deleteBtns.get(0).click();

            new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                    getProductCount() < initialCount
            );
        }
    }

    public void deleteAllProducts() {
        while (!isCartEmpty()) {
            deleteFirstProduct();
            waitForCartToUpdate();
        }
    }



    public boolean isCartEmpty() {
        List<WebElement> productRows = driver.findElements(By.xpath("//tr/td[2]"));
        return productRows.isEmpty();
    }

    public boolean isCartEmptyMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
            return message.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isPlaceOrderButtonDisplayed() {
        return driver.findElement(placeOrderButton).isDisplayed();
    }

    public boolean isPlaceOrderButtonEnabled() {
        return driver.findElement(placeOrderButton).isEnabled();
    }


    public void placeOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement placeOrderButtonElement = driver.findElement(placeOrderButton);
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButtonElement)).click();
    }

    public void waitForCartToUpdate() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
            List<WebElement> rows = driver.findElements(By.xpath("//tr/td[2]"));
            return rows.isEmpty();
        });
    }

}
