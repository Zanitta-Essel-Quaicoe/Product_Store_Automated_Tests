package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ContactPage;
import pages.HomePage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ContactPageTests extends BaseTest {
    private HomePage homePage;
    private ContactPage contactPage;

    @BeforeEach
    public void setUp() throws IOException {
        super.setUp();
        homePage = new HomePage(driver);
        contactPage = new ContactPage(driver);
        homePage.open();
    }

    @Test
    public void verifyContactPageIsAccessibleFromMainNav() {
        homePage.clickContactLink();
        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be visible after clicking Contact link");
    }

    @Test
    public void verifyContactFormIsDisplayed() {
        homePage.clickContactLink();
        assertTrue(contactPage.isContactFormDisplayed(), "Contact form should be displayed in the modal");
    }

    @Test
    public void verifyNameFieldAcceptsOnlyAlphabeticCharacters() {
        homePage.clickContactLink();
        contactPage.enterName("John123");
        assertFalse(contactPage.isNameFieldValid(), "Name field should reject non-alphabetic characters");
    }

    @Test
    public void verifyEmailFieldAcceptsOnlyValidEmailFormat() {
        homePage.clickContactLink();
        contactPage.enterEmail("invalid-email");
        assertFalse(contactPage.isEmailFieldValid(), "Email field should reject invalid email format");
    }

    @Test
    public void verifyConfirmationMessageAppearsAfterSuccessfulFormSubmission() {
        homePage.clickContactLink();
        contactPage.enterName("John");
        contactPage.enterEmail("john@example.com");
        contactPage.enterMessage("This is a test message.");
        contactPage.clickSendMessage();
        assertTrue(contactPage.isSubmissionConfirmed(), "Confirmation alert should appear after successful contact form submission");
    }

    @Test
    public void verifyFormDoesNotSubmitWithInvalidOrMissingData() {
        homePage.clickContactLink();
        contactPage.enterName("");
        contactPage.enterEmail("invalid-email");
        contactPage.enterMessage("");
        contactPage.clickSendMessage();
        String alertText = contactPage.getAlertTextAndAccept();
        assertNotEquals("Thanks for the message!!", alertText, "Form should not submit with invalid or missing data");
    }
}
