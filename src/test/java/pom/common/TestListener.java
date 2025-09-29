package pom.common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) return;
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String name = result.getMethod().getMethodName() + "_" + LocalDateTime.now().format(TS) + ".png";
            Path outDir = Path.of("target", "screenshots");
            Files.createDirectories(outDir);
            File dest = outDir.resolve(name).toFile();
            FileUtils.copyFile(src, dest);
            Reporter.log("Saved screenshot: " + dest.getAbsolutePath(), true);
        } catch (Exception e) {
            Reporter.log("Failed to capture screenshot: " + e.getMessage(), true);
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
