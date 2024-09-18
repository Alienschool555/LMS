package tests;

import com.microsoft.playwright.Locator;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import tests.helpers.TestData;
import utils.runner.ProjectProperties;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PaymentTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Profile")
    @TmsLink("bagw135rbhfe")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "    Objective: To verify that the user can successfully navigate to the Profile page."
            + "Assert that the headers H3 display as expected: “Account”, “Your Courses”, “Payment method”.")
    @Test(description = "TC1359-01 - Profile Page has Course Purchase course options.")
    public void testProfilePageDisplaysBuyNewCourseHeaders() {
        final String profileUrl = ProjectProperties.BASE_URL + TestData.PROFILE_END_POINT;

        ProfilePage profilePage =
                new HomePage(getPage()).init()
                        .clickProfileButton();

        Allure.step("Assert that user can land on Profile page (" + TestData.PROFILE_END_POINT + ").");
        assertThat(getPage()).hasURL(profileUrl);

        final Locator accountHeading = profilePage.getAccount();
        final Locator billingButton = profilePage.getBillingButton();
        final Locator addANewCourseButton = profilePage.getAddANewCourseButton();

        Allure.step("Assert that 'Account' heading is visible.");
        assertThat(accountHeading).isVisible();

        Allure.step("Assert that 'Billing' button is visible.");
        assertThat(billingButton).isVisible();

        Allure.step("Assert that 'Add a New Course' button is visible.");
        assertThat(addANewCourseButton).isVisible();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("AddNewCourse")
    @TmsLink("wclaf9sah357")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify that clicking the '+ Add a new course' button on the Profile page opens the 'Add new course' page."
            + " and verify the 'Add new course' page is opened.")
    @Test(description = "TC1359-02 - Opening the 'Add New Course' Page from Profile")
    public void testAddNewCourseButtonNavigation() {
        final String addNewCourseUrl = ProjectProperties.BASE_URL + TestData.ADD_NEW_COURSE_END_POINT;

        AddNewCoursePage addNewCoursePage =
                new HomePage(getPage()).init()
                        .clickProfileButton()
                        .clickAddANewCourseButton();

        Allure.step("Assert that user can land on Add new course page (" + TestData.ADD_NEW_COURSE_END_POINT + ").");
        assertThat(getPage()).hasURL(addNewCourseUrl);

        Allure.step("Assert that 'Add new course' page header is '" + TestData.ADD_NEW_COURSE + "'.");
        assertThat(addNewCoursePage.getAddNewCourseHeader()).hasText(TestData.ADD_NEW_COURSE);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("AddNewCourse")
    @TmsLink("du8bq42dps6b")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To confirm that clicking the 'Get' button on the " +
            "'Add new course' page opens the 'Choose a product' modal window."
            + " and Verify that the user is redirected to the 'Choose a product' page, displayed as a modal window.")
    @Test(description = "TC1359-03 - Opening the 'Choose a Product' Modal by Clicking 'Get' Button”.")
    public void testOpenChooseAProductModalByClickAGetButton() {
        AddNewCourseModal addNewCourseModal =
                new HomePage(getPage()).init()
                        .clickProfileButton()
                        .clickAddANewCourseButton()
                        .clickGetButton();

        final Locator modalHeading = addNewCourseModal.getChooseAProductHeading();
        final Locator mvpSubscribeButton = addNewCourseModal.getMvpSubscribeButton();

        Allure.step("Assert that after clicking on 'Get' button, the new modal window is opened.");
        assertThat(addNewCourseModal.getDialog()).isVisible();

        Allure.step("Assert that the new modal window displays the heading.");
        assertThat(modalHeading).isVisible();

        Allure.step("Assert that the heading text is '" + TestData.CHOOSE_A_PRODUCT + "'.");
        assertThat(modalHeading).hasText(TestData.CHOOSE_A_PRODUCT);

        Allure.step("Assert that the MVP plan 'Subscribe' button is visible.");
        assertThat(mvpSubscribeButton).isVisible();

        Allure.step("Assert that the MVP plan 'Subscribe' button is enabled.");
        assertThat(mvpSubscribeButton).isEnabled();
    }

//    @Severity(SeverityLevel.NORMAL)
//    @Story("AddNewCourse")
//    @TmsLink("wxcm7w4fhzq0")
//    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
//            + "Objective: To verify the user's ability to purchase the Lifetime course."
//            + " and verify that the user is redirected to the Lifetime course purchase page.")
//    @Test(description = "TC1359-04 - Purchasing MVP Course (Gold package)")
//    public void testClickOnTheMVPSubscribeButton() {
//        StripeModal addNewCourseModal =
//                new HomePage(getPage()).init()
//                        .clickProfileButton()
//                        .clickAddANewCourseButton()
//                        .clickGetButton()
//                        .clickMVPSubscribeButton();
//
//        final Locator goldHeading = addNewCourseModal.getGoldHeading();
//        final Locator silverHeading = addNewCourseModal.getSilverHeading();
//        final Locator bronzeHeading = addNewCourseModal.getBronzeHeading();
//        final Locator purchaseButton = addNewCourseModal.getPurchaseButton();
//
////        Allure.step("Assert that the 'Gold' option is available.");
////        assertThat(goldHeading).isVisible();
//
//        Allure.step("Assert that the 'Silver' option is not available.");
//        assertThat(silverHeading).not().isVisible();
//
//        Allure.step("Assert that the 'Bronze' option is not available.");
//        assertThat(bronzeHeading).not().isVisible();
//
//        Allure.step("Assert that the 'Purchase' button is visible.");
//        assertThat(purchaseButton).isVisible();
//
//        Allure.step("Assert that the only one 'Purchase' button is present.");
//        assertThat(purchaseButton).hasCount(1);
//
//        Allure.step("Assert that the 'Purchase' button is enabled.");
//        assertThat(purchaseButton).isEnabled();
//    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StripeModal")
    @TmsLink("8afbdan400ta")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify the user's ability to input payment information after clicking "
                   + "the 'MVPSubscribeButton' button."
            + " and verify the User is redirected to the Stripe Payments Options Modal.")
    @Test(description = "TC1359-05 - Inputting Payment Information After Clicking 'MVPSubscribeButton'")
    public void testMVPSubscribeButtonOpensStripeModal() {
        StripeModal stripeModal =
                new HomePage(getPage()).init()
                        .clickProfileButton()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickMVPSubscribeButton();

        final List<Locator> paymentsElement = stripeModal.getPaymentsElement();

        Allure.step("Assert that Payments elements are attached.");
        paymentsElement.forEach(paymentElement -> assertThat(paymentElement).isAttached());

        Allure.step("Assert that Payments elements are visible.");
        paymentsElement.forEach(paymentElement -> assertThat(paymentElement).isVisible());

        Allure.step("Assert that Stripe modal button has text 'Enroll Now'.");
        Assert.assertTrue(stripeModal.getEnrollNowButton().isVisible());
        assertThat(stripeModal.getEnrollNowButton()).containsText("Enroll Now:");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("StripeModal")
    @TmsLink("appnuxx2ck9l")
    @Description("LMS-TC1359 Покупка курса.https://app.qase.io/plan/LMS/1?case=1359"
            + "Objective: To verify the user's ability to submit payment after entering valid credit card information."
            + " and verify that the new course is shown in the Your Courses list on the Profile page.")
    @Test(description = "TC1359-06 - Submitting Payment with Valid Credit Card")
    public void testE2EPurchaseLifeTimeCourse() {
        StripeModal stripeModal =
                new HomePage(getPage()).init()
                        .clickProfileButton()
                        .clickAddANewCourseButton()
                        .clickGetButton()
                        .clickMVPSubscribeButton()
                        .inputCreditCardNumber(TestData.PAYMENT_CARD_NUMBER)
                        .inputCardExpirationDate(TestData.CARD_EXPIRATION_DATE)
                        .inputCardCVC(TestData.CVC)
                        .inputCardCountry(TestData.COUNTRY)
                        .inputZipCode(TestData.ZIP_CODE);

        final List<Locator> paymentsElements = stripeModal.getPaymentsElement();

//        Allure.step("Assert that Stripe payment element is attached.");
//        assertThat(stripeElement).isAttached();
//
//        Allure.step("Assert that Stripe payment element is visible.");
//        assertThat(stripeElement).isVisible();
    }
}
