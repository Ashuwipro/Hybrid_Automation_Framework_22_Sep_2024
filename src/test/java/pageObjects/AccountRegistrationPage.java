package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountRegistrationPage extends BasePage{

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstName;
    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastName;
    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;
    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtTelephone;
    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;
    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtConfirmPassword;
    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkdPolicy;
    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;
    @FindBy(xpath = "//h1[.='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setFirstName(String firstName){
        txtFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastName){
        txtLastName.sendKeys(lastName);
    }

    public void setEmail(String email){
        txtEmail.sendKeys(email);
    }

    public void setTelephone(String telephone){
        txtTelephone.sendKeys(telephone);
    }

    public void setPassword(String password){
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword(String password){
        txtConfirmPassword.sendKeys(password);
    }

    public void setPrivacyPolicy(){
        chkdPolicy.click();
    }

    public void clickContinue(){
        //solution1
        btnContinue.click();

        //solution2
        //btnContinue.submit();

        //solution3
        //Actions act = new Actions(driver);
        //act.moveToElement(btnContinue).click().perform();

        //solution4
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].click();", btnContinue);

        //solution5
        //btnContinue.sendKeys(Keys.RETURN);

        //solution6
        //WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //webDriverWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
    }

    public String getConfirmationMsg(){
        try{
            return msgConfirmation.getText();
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
