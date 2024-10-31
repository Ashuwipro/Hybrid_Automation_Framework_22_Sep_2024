package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
    public void verify_loginDDT(String email, String password, String result) {

        logger.info("***** Starting TC003_LoginDDT *****");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(email);
            loginPage.setPassword(password);
            loginPage.clickLogin();

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();

            if (result.equalsIgnoreCase("Valid")) {
                if (targetPage) {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.fail();
                }
            } else {
                if (targetPage) {
                    myAccountPage.clickLogout();
                    Assert.fail();
                } else {
                    Assert.assertTrue(true);
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }

        logger.info("***** Finished TC003_LoginDDT *****");
    }
}
