package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[.='My Account']")
    WebElement lnkMyAccount;

    @FindBy(xpath = "//a[.='Register']")
    WebElement lnkRegister;

    @FindBy(xpath = "//a[.='Login']")
    WebElement lnkLogin;

    public void clickMyAccount() {
        lnkMyAccount.click();
    }

    public void clickRegister() {
        lnkRegister.click();
    }

    public void clickLogin() {
        lnkLogin.click();
    }
}
