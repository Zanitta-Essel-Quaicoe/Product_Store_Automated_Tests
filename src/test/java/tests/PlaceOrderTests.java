package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.HomePage;
import pages.PlaceOrderPage;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceOrderTests extends BaseTest {
    private CartPage cartPage;
    private PlaceOrderPage placeOrderPage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        HomePage homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        placeOrderPage = new PlaceOrderPage(driver);

        homePage.open();
        homePage.clickFirstProduct();
        placeOrderPage.addToCart();
        cartPage.navigateToCart();
    }

    @Test
    public void verifyPlaceOrderButtonExists() {
        assertTrue(cartPage.isPlaceOrderButtonDisplayed(), "Place Order button should be displayed");
    }

    @Test
    public void verifyPlaceOrderButtonIsClickableWhenProductsAreInTheCart() {
        assertTrue(cartPage.getProductCount() > 0, "Cart should contain at least one product");
        assertTrue(cartPage.isPlaceOrderButtonEnabled(), "Place Order button should be clickable when products are in cart");
    }

    @Test
    public void verifyPlaceOrderButtonIsDisabledOrHiddenWhenCartIsEmpty() {
        cartPage.deleteAllProducts();  // New method you must implement
        cartPage.waitForCartToUpdate(); // Optional wait method if needed

        assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
        assertFalse(cartPage.isPlaceOrderButtonEnabled(), "Place Order button should be disabled when cart is empty");
        assertTrue(cartPage.isCartEmptyMessageDisplayed(), "Empty cart message should be visible when no products are in the cart");
    }


    @Test
    public void verifyOrderFormAppearsUponClickingPlaceOrder() {
        cartPage.placeOrder();
        assertTrue(placeOrderPage.isOrderFormDisplayed(), "Order form should appear after clicking Place Order");
    }

    @Test
    public void verifyPlacingOrderWithEmptyForm() {
        cartPage.placeOrder();
        placeOrderPage.placeOrder();

        String alertText = placeOrderPage.getAlertTextAndAccept();
        assertEquals(alertText, "Please fill out Name and Creditcard.");
    }




    @Test
    public void verifyOrderFormSubmitsWithOnlyRequiredFields() {
        cartPage.placeOrder();
        placeOrderPage.enterName("John Doe");
        placeOrderPage.enterCreditCard("1234567890123456");
        placeOrderPage.placeOrder();

        assertTrue(placeOrderPage.isConfirmationMessageDisplayed(), "Order should be submitted when required fields are filled");
    }

    @Test
    public void verifyNameFieldAcceptsOnlyAlphabeticCharacters() {
        cartPage.placeOrder();
        placeOrderPage.enterName("John123");
        assertFalse(placeOrderPage.isNameFieldValid(), "Name field should reject non-alphabetic characters");
    }

    @Test
    public void verifyCreditCardFieldAcceptsOnly16DigitNumericInput() {
        cartPage.placeOrder();
        placeOrderPage.enterCreditCard("abc123456789");
        assertFalse(placeOrderPage.isCreditCardFieldValid(), "Credit Card field should reject non-16-digit numeric input");
    }

    @Test
    public void verifyConfirmationMessageDisplaysAfterSuccessfulOrder() {
        cartPage.placeOrder();
        placeOrderPage.enterName("John Doe");
        placeOrderPage.enterCountry("USA");
        placeOrderPage.enterCity("New York");
        placeOrderPage.enterCreditCard("1234567890123456");
        placeOrderPage.enterMonth("12");
        placeOrderPage.enterYear("2025");
        placeOrderPage.placeOrder();
        assertTrue(placeOrderPage.isConfirmationMessageDisplayed(), "Confirmation message should display after successful order");
    }
}
