package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTests extends BaseTest {

    @Test
    public void verifyHomepageAccessibility() {
        HomePage home = new HomePage(driver);
        home.open();
        assertEquals("STORE", driver.getTitle());
    }

    @Test
    public void verifyNavbarIsDisplayed() {
        HomePage home = new HomePage(driver);
        home.open();
        assertTrue(home.isNavbarDisplayed(), "Navbar should be displayed");
    }

    @Test
    public void verifyCarouselIsDisplayed() {
        HomePage home = new HomePage(driver);
        home.open();
        assertTrue(home.isCarouselDisplayed(), "Carousel should be visible");
    }

    @Test
    public void verifyCarouselIndicatorsNavigation() {
        HomePage home = new HomePage(driver);
        home.open();
        int count = home.getCarouselIndicatorsCount();
        assertTrue(count >= 1, "Carousel indicators should be present");
    }

    @Test
    public void verifyCarouselArrowNavigation() {
        HomePage home = new HomePage(driver);
        home.open();
        home.clickNextCarousel();
        home.clickPrevCarousel();
        assertTrue(home.isCarouselDisplayed());
    }

    @Test
    public void verifyCarouselResponsiveness() {
        HomePage home = new HomePage(driver);
        home.open();

        driver.manage().window().setSize(new Dimension(375, 667)); // iPhone X viewport
        assertTrue(home.isCarouselDisplayed(), "Carousel should be visible on mobile");

        driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet viewport
        assertTrue(home.isCarouselDisplayed(), "Carousel should be visible on tablet");

        driver.manage().window().maximize(); // Desktop
        assertTrue(home.isCarouselDisplayed(), "Carousel should be visible on desktop");
    }

    @Test
    public void verifyProductListingDisplay() {
        HomePage home = new HomePage(driver);
        home.open();
        assertTrue(home.getProductCards().size() > 0, "There should be product cards listed");
    }

    @Test
    public void verifyProductDetailNavigation() {
        HomePage home = new HomePage(driver);
        home.open();
        home.clickFirstProduct();
        assertTrue(driver.getCurrentUrl().contains("prod.html"), "Should navigate to product detail page");
    }

    @Test
    public void validateProductCardConsistency() {
        HomePage home = new HomePage(driver);
        home.open();
        home.getProductCards().forEach(card -> {
            assertTrue(card.isDisplayed(), "Each product card should be visible");
        });
    }

    @Test
    public void verifyProductCategoryFiltering() {
        HomePage home = new HomePage(driver);
        home.open();
        home.clickCategoryByName("Phones");
        assertTrue(home.getProductCards().size() > 0, "Product list should update for Phones category");
    }
}
