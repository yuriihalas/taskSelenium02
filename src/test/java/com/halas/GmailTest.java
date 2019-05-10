package com.halas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.halas.DriverManager.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GmailTest {
    private static final Logger LOG = LogManager.getLogger(GmailTest.class);
    private static final String USER_NAME = "paprika0015@gmail.com";
    private static final String PASSWORD = "423489123789op";
    private static final String FIELD_EMAIL_TO = "groot.epam@gmail.com";
    private static final String FIELD_THEME = "Final battle";
    private static final String FIELD_JUST_TEXT = "Hello, I am a robot!";


    @Test
    void testLogIn() throws InterruptedException {
        //authorization block
        WebElement emailAreaElement = getWebDriver().findElement(By.id("identifierId"));
        emailAreaElement.sendKeys(USER_NAME);
        WebElement buttonEmailNextElement = getWebDriver().findElement(By.id("identifierNext"));
        buttonEmailNextElement.click();
        WebElement passwordAreaElement = getWebDriverWait().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        passwordAreaElement.sendKeys(PASSWORD);
        WebElement buttonPasswordNextElement = getWebDriver().findElement(By.id("passwordNext"));
        buttonPasswordNextElement.click();
        //sent message block
        WebElement buttonWriteSomeoneElement = getWebDriver().findElement(By.cssSelector("[jscontroller='DUNnfe'] [role='button']"));
        buttonWriteSomeoneElement.click();
        WebElement textToWhoSendElement = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='to']")));
        textToWhoSendElement.sendKeys(FIELD_EMAIL_TO);
        WebElement textThemeToSendElement = getWebDriver().findElement(By.name("subjectbox"));
        textThemeToSendElement.sendKeys(FIELD_THEME);
        WebElement justTextToSendElement = getWebDriver().findElement(By.xpath("//div[@role='textbox']"));
        justTextToSendElement.sendKeys(FIELD_JUST_TEXT);
        WebElement buttonSendMessageElement = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[role='button'][data-tooltip*='(Ctrl –Enter)']")));
        buttonSendMessageElement.click();
        String currentTime = new MyDate().getCurrentTimeHoursMinutes();

        //will found element sent and assert that message was sent
        WebElement sentMessagesElement = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='sent']")));
        sentMessagesElement.click();
        TimeUnit.SECONDS.sleep(15);
        WebElement onTopFirstMessageSentElement = getWebDriver().findElement((By.cssSelector("div.AO div[role='main'] tbody>tr:first-child>*[tabindex]")));
        String textExpected = onTopFirstMessageSentElement.getText();
        LOG.info("First message: " + textExpected);
        assertTrue(textExpected.startsWith(FIELD_THEME) && textExpected.endsWith(FIELD_JUST_TEXT));

        WebElement timeMessageSentElement = getWebDriver().findElement(By.cssSelector("div.AO div[role='main'] tbody>tr:first-child>td:nth-last-child(2)>*"));
        LOG.info("Time message: " + timeMessageSentElement.getAttribute("title"));
        String actualTime = timeMessageSentElement.getText();
        assertEquals(currentTime, actualTime);

        quitWebDriver();
    }

}
