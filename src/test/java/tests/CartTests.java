package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTests extends BaseTest {

    private HomePage homePage;
    private CartPage cartPage;
    private ProductPage productPage;

    @BeforeEach
    public void initPages() {
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    @Order(1)
    public void verifyCartNavigationFromNavbar() {
        cartPage.navigateToCart();
        assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @Test
    @Order(2)
    public void verifyAddToCartWithoutLogin() {
        homePage.clickProduct("Samsung galaxy s6");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        assertTrue(cartPage.isProductInCart("Samsung galaxy s6"));
    }

    @Test
    @Order(3)
    public void verifyAddToCartFromProductPage() {
        homePage.clickProduct("Nokia lumia 1520");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        assertTrue(cartPage.isProductInCart("Nokia lumia 1520"));
    }

    @Test
    @Order(4)
    public void verifyCartDisplaysProductsAndTotalCost() {
        homePage.clickProduct("Samsung galaxy s7");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        assertTrue(cartPage.getTotalCost() > 0);
        assertTrue(cartPage.getProductCount() > 0);
    }

    @Test
    @Order(5)
    public void verifySameProductAddsMultipleRows() {
        homePage.clickProduct("Sony xperia z5");
        productPage.addToCartAndAcceptAlert();
        homePage.open(); // go back to product list
        homePage.clickProduct("Sony xperia z5");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        // Count how many times 'Sony xperia z5' appears
        long count = cartPage.getAllProductNames().stream()
                .filter(name -> name.equalsIgnoreCase("Sony xperia z5"))
                .count();

        assertEquals(2, cartPage.getProductRowCount("Sony xperia z5"));

    }


    @Test
    @Order(6)
    public void verifyProductRemovalFromCart() {
        homePage.clickProduct("Nexus 6");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        cartPage.deleteFirstProduct();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        assertTrue(cartPage.isCartEmpty());
    }

    @Test
    @Order(7)
    public void verifyTotalCostUpdatesOnCartChange() {
        homePage.clickProduct("Samsung galaxy s7");
        productPage.addToCartAndAcceptAlert();
        cartPage.navigateToCart();
        int totalBefore = cartPage.getTotalCost();
        cartPage.deleteFirstProduct();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        int totalAfter = cartPage.getTotalCost();
        assertTrue(totalAfter < totalBefore);
    }

    @Test
    @Order(8)
    public void verifyCartRetainsItemsUntilCleared() {
        homePage.clickProduct("Nokia lumia 1520");
        productPage.addToCartAndAcceptAlert();
        driver.navigate().refresh();
        cartPage.navigateToCart();
        assertTrue(cartPage.isProductInCart("Nokia lumia 1520"));
    }

    @Test
    @Order(9)
    public void verifyEmptyCartBehavior() {
        cartPage.navigateToCart();
        assertTrue(cartPage.isCartEmpty());
    }

    @Test
    @Order(10)
    public void verifyMultiTabCartConsistency() {
        homePage.clickProduct("Samsung galaxy s7");
        productPage.addToCartAndAcceptAlert();
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get("https://www.demoblaze.com/cart.html");
        assertTrue(cartPage.isProductInCart("Samsung galaxy s7"));
    }
}
