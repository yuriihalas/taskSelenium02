package com.halas.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final String PATH_TO_DRIVER = "src/main/resources/chromedriver.exe";
    private static final String WEB_DRIVER_NAME = "webdriver.chrome.driver";

    private static WebDriver driver = null;
    private static WebDriverWait driverWait = null;

    private DriverManager() {
    }

    public static WebDriver getWebDriver() {
        if (Objects.isNull(driver)) {
            System.setProperty(WEB_DRIVER_NAME, PATH_TO_DRIVER);
            driver = new ChromeDriver();
            driver.manage().timeouts()
                    .implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static WebDriverWait getWebDriverWait() {
        if (Objects.isNull(driverWait)) {
            if (Objects.isNull(driver)) {
                getWebDriver();
            }
            driverWait = new WebDriverWait(driver, 50);
        }
        return driverWait;
    }
}
