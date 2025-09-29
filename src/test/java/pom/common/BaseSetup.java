package pom.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import org.testng.annotations.Parameters;

public class BaseSetup {
    public WebDriver setDriver(String browserType, String appURL) {
        WebDriver driver;

        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(appURL);
        }
        return driver;
    }

    private WebDriver initChromeDriver(String appURL) {
        WebDriver driver;

        System.out.println("Launching Chrome browser...");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }

        try {
            Path tempProfile = Files.createTempDirectory("chrometmp-" + UUID.randomUUID());
            options.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        options.addArguments("--incognito",
                "--disable-extensions",
                "--disable-notifications",
                "--disable-popup-blocking",
                "--no-first-run",
                "--no-default-browser-check",
                "--password-store=basic",
                "--use-mock-keychain");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver d = new ChromeDriver(options);
        d.manage().window().maximize();
        d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        d.get(appURL);
        return d;
    }

    private WebDriver initFirefoxDriver(String appURL) {
        WebDriver driver;

        FirefoxOptions options = new FirefoxOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("-headless");
        }

        try {
            Path tmpProfile = Files.createTempDirectory("firefoxtmp-" + UUID.randomUUID());
            options.addArguments("-profile");
            options.addArguments(tmpProfile.toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver d = new FirefoxDriver(options);
        d.manage().window().maximize();
        d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        d.get(appURL);
        return d;
    }

    @Parameters({"browserType", "appURL"})
    @BeforeMethod(alwaysRun = true)
    public void initializeTestBaseSetup(@Optional("chrome") String browserType,
                                        @Optional("https://www.saucedemo.com/") String appURL,
                                        org.testng.ITestContext context) {
        WebDriver driver = setDriver(browserType, appURL);
        DriverManager.setDriver(driver);
    }

    @AfterMethod
    public void closeDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quit();
        }
    }
}
