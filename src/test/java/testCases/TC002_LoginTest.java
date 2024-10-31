package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verify_login() {
        logger.info("***** Starting TC002_LoginTest *****");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(properties.getProperty("email"));
            loginPage.setPassword(properties.getProperty("password"));
            loginPage.clickLogin();

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();
            Assert.assertTrue(targetPage, "Login Failed");
        } catch (Exception e) {
            Assert.fail();
        }

        logger.info("***** Finished TC002_LoginTest *****");
    }
}
