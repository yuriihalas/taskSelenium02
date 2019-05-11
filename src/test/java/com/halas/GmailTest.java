package com.halas;

import com.halas.date.MyDate;
import com.halas.utils.RandomSentence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.halas.driver.DriverManager.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GmailTest {
    private static final Logger LOG = LogManager.getLogger(GmailTest.class);
    private static final String HOME_PAGE = "https://mail.google.com/";
    private static final String USER_NAME = "paprika0015@gmail.com";
    private static final String PASSWORD = "423489123789op";
    private static final String FIELD_EMAIL_TO = "groot.epam@gmail.com";
    private static final String FIELD_THEME = "Final battle";

    @BeforeClass
    void initObjects() {
        getWebDriver().get(HOME_PAGE);
    }

    @Test
    void testLogIn() {
        //for check is difference between minutes in time is <= 1
        MyDate myDate = new MyDate();
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
        String justTextToSendUser = RandomSentence.getSentence();
        //button sendMessage and then will open form to send message
        WebElement buttonWriteSomeoneElement = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[jscontroller='DUNnfe'] [role='button']")));
        buttonWriteSomeoneElement.click();
        //sent message form/block
        WebElement textToWhoSendElement = getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='to']")));
        textToWhoSendElement.sendKeys(FIELD_EMAIL_TO);
        WebElement textThemeToSendElement = getWebDriver().findElement(By.name("subjectbox"));
        textThemeToSendElement.sendKeys(FIELD_THEME);
        WebElement justTextToSendElement = getWebDriver().findElement(By.xpath("//div[@role='textbox']"));
        justTextToSendElement.sendKeys(justTextToSendUser);
        WebElement buttonSendMessageElement = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[role='button'][data-tooltip*='(Ctrl –Enter)']")));
        buttonSendMessageElement.click();
        //current time in HH:mm
        String currentTime = myDate.getCurrentTimeHoursMinutes();
        //will found element sent and assert that message was sent
        WebElement sentMessagesElement = getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='sent']")));
        sentMessagesElement.click();
        //two waiter for load all first element on new page AND load textThatSent
        getWebDriverWait()
                .until(ExpectedConditions.not(ExpectedConditions.attributeToBe(By.cssSelector(
                        "div[role='main'] tbody > tr:first-child > td:nth-child(5) > div:nth-child(2) > span[email][name]"), "email", USER_NAME)));
        getWebDriverWait()
                .until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector(
                        "div[role=\"main\"] tbody > tr:first-child > *[tabindex] > div > div > span"), " ")));
        WebElement onTopFirstMessageSentElement = getWebDriver().findElement((By.cssSelector("div.AO div[role='main'] tbody>tr:first-child>*[tabindex]")));
        String textExpected = onTopFirstMessageSentElement.getText();
        LOG.info("First message: " + textExpected);
        assertTrue(textExpected.startsWith(FIELD_THEME));
        assertTrue(textExpected.endsWith(justTextToSendUser));
        WebElement timeMessageSentElement = getWebDriver().findElement(By.cssSelector("div.AO div[role='main'] tbody>tr:first-child>td:nth-last-child(2)>*"));
        LOG.info("Time message: " + timeMessageSentElement.getAttribute("title"));
        String actualTime = timeMessageSentElement.getText();
        assertTrue(myDate.isOnePlusOrOneLessMinutes(currentTime, actualTime));
    }

    @AfterClass
    void closeObjects() {
        getWebDriver().quit();
    }
}
