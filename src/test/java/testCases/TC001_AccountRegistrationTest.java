package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

import java.time.Duration;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_account_registration(){

        logger.info("***** Starting TC001_AccountRegistrationTest *****");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("***** Clicked on My Account Link *****");

            homePage.clickRegister();
            logger.info("***** Clicked on Register Link *****");

            AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
            logger.info("***** Providing Customer Details *****");
            accountRegistrationPage.setFirstName(randomString());
            accountRegistrationPage.setLastName(randomString());
            accountRegistrationPage.setEmail(randomString() + "@mail.com");
            accountRegistrationPage.setTelephone(randomNumber());

            String password = randomAlphaNumeric();
            accountRegistrationPage.setPassword(password);
            accountRegistrationPage.setConfirmPassword(password);

            accountRegistrationPage.setPrivacyPolicy();
            accountRegistrationPage.clickContinue();

            logger.info("***** Validating Expected Message *****");
            String confirmMessage = accountRegistrationPage.getConfirmationMsg();
            Assert.assertEquals(confirmMessage, "Your Account Has Been Created!");
        }catch(Exception e){
            logger.error("***** Test Failed *****");
            logger.debug("***** Debug Logs *****");
            Assert.fail();
        }

        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }
}
