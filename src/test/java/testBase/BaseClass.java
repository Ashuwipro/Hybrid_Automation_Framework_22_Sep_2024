package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.*;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties properties;

    public static Process process;
    public static final int PORT = 4444;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String browser) throws IOException {

        //loading config.properties
        FileReader fileReader = new FileReader("./src/test/resources/config.properties");
        properties = new Properties();
        properties.load(fileReader);

        logger = LogManager.getLogger(this.getClass());

        String command = "java -jar selenium-server-4.25.0.jar standalone";
        String directory = Paths.get(System.getProperty("user.dir")).getParent().toString();
        String message = "Started Selenium Standalone";
        boolean isGridStarted = executeCommand(command, directory, message);

        if (isGridStarted) {
            System.out.println("Grid started successfully");
        } else {
            System.out.println("Grid failed to start");
        }

        if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("windows")) {
                desiredCapabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                desiredCapabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching os");
                return;
            }

            switch (browser.toLowerCase()) {
                case "chrome":
                    desiredCapabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    desiredCapabilities.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    desiredCapabilities.setBrowserName("firefox");
                    break;
                case "safari":
                    desiredCapabilities.setBrowserName("safari");
                    break;
                default:
                    System.out.println("No matching browser");
                    return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), desiredCapabilities);
        }

        if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    logger.error("Invalid browser name...");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        //driver.get("https://demo.opencart.com/en-gb?route=common/home");
        //driver.get("https://tutorialsninja.com/demo/");
        driver.get(properties.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        driver.quit();
        endCommand();
    }

    public String randomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String randomAlphaNumeric() {
        String generatedString = RandomStringUtils.randomAlphabetic(3);
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        return generatedString + "@" + generatedNumber;
    }

    public String captureScreen(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir").concat("\\screenshots\\").concat(name)
                .concat("_").concat(timeStamp).concat(".png");
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }

    public static boolean executeCommand(String command, String directory, String message) {
        if (!isPortInUse(PORT)) {
            try {
                // Set up the process builder with the command and working directory
                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                processBuilder.directory(new java.io.File(directory));

                // Start the process
                process = processBuilder.start();

                // Read the output in real time
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Print each line of output
                    // Check if the line contains the expected server start message
                    if (line.contains(message)) {
                        return true;
                    }
                }

                // Wait for the process to finish if it exits prematurely
                process.waitFor();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void endCommand() {
        if (process != null && process.isAlive()) {
            process.destroy();
            try {
                if (!process.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    process.destroyForcibly(); // Force stop if it doesn't exit in time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Command ended");
        }
    }

    public static boolean isPortInUse(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // If this succeeds, the port is available
            return false;
        } catch (IOException e) {
            // If an IOException occurs, the port is likely in use
            return true;
        }
    }
}
