package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String reportName;

    public void onStart(ITestContext iTestContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report-".concat(timeStamp).concat(".html");
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir").concat("/reports/").concat(reportName));

        extentSparkReporter.config().setDocumentTitle("OpenCart Automation Report");
        extentSparkReporter.config().setReportName("OpenCart Functional Testing");
        extentSparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Application", "OpenCart");
        extentReports.setSystemInfo("Module", "Admin");
        extentReports.setSystemInfo("Sub Module", "Customers");
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "QA");

        String os = iTestContext.getCurrentXmlTest().getParameter("os");
        extentReports.setSystemInfo("Operating System", os);

        String browser = iTestContext.getCurrentXmlTest().getParameter("browser");
        extentReports.setSystemInfo("Browser", browser);

        List<String> includedGroups = iTestContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty())
            extentReports.setSystemInfo("Groups", includedGroups.toString());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        extentTest = extentReports.createTest(iTestResult.getTestClass().getName());
        extentTest.assignCategory(iTestResult.getMethod().getGroups());
        extentTest.log(Status.PASS, iTestResult.getName().concat(" got successfully executed"));
    }

    public void onTestFailure(ITestResult iTestResult) {
        extentTest = extentReports.createTest(iTestResult.getTestClass().getName());
        extentTest.assignCategory(iTestResult.getMethod().getGroups());

        extentTest.log(Status.FAIL, iTestResult.getName().concat(" got failed"));
        extentTest.log(Status.INFO, iTestResult.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(iTestResult.getName());
            extentTest.addScreenCaptureFromPath(imgPath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult iTestResult) {
        extentTest = extentReports.createTest(iTestResult.getTestClass().getName());
        extentTest.assignCategory(iTestResult.getMethod().getGroups());
        extentTest.log(Status.SKIP, iTestResult.getName().concat(" got skipped"));
        extentTest.log(Status.INFO, iTestResult.getThrowable().getMessage());
    }

    public void onFinish(ITestContext iTestContext) {
        extentReports.flush();

        String pathOfExtentReport = System.getProperty("user.dir").concat("/reports/").concat(reportName);
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

//        try{
//            URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+reportName);
//
//            //Create email message
//            ImageHtmlEmail imageHtmlEmail = new ImageHtmlEmail();
//            imageHtmlEmail.setDataSourceResolver(new DataSourceClassPathResolver(url.getPath()));
//            imageHtmlEmail.setHostName("smtp.googlemail.com"); //gmail
//            imageHtmlEmail.setSmtpPort(465);
//            imageHtmlEmail.setAuthenticator(new DefaultAuthenticator("ashu.pal123@mail.com", "Ashu123#"));
//            imageHtmlEmail.setSSLOnConnect(true);
//            imageHtmlEmail.setFrom("ashu.pal@mail.com"); //sender
//            imageHtmlEmail.setSubject("Test Results");
//            imageHtmlEmail.setMsg("Please find attached report...");
//            imageHtmlEmail.addTo("ashu.pal@mail.com"); //receiver
//            imageHtmlEmail.attach(url, "extent report", "please check report...");
//            imageHtmlEmail.send();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
}
