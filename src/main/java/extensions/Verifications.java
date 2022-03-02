package extensions;

import Utilities.CommonOps;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.fail;

public class Verifications extends CommonOps {

    @Step("verifyTextInElement")
    public static void verifyTextInElement(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        assertEquals(elm.getText(), text);
    }

    @Step("verify equals text")
    public static void soft_verifyTextEquals(String actual, String expected) {
        assertEquals(actual, expected);
    }

    @Step("verify equals text")
    public static void verifyTextEquals(String actual, String expected) {
        assertEquals(actual, expected);
    }

    @Step("softVerifyTextInElement")
    public static void softVerifyTextInElement(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        softAssert.assertEquals(elm.getText(), text);
    }


    @Step("softVerifyTextInElementContains")
    public static void softVerifyTextInElementContains(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        softAssert.assertTrue(elm.getText().contains(text));
    }

    @Step("verifyTextInElementContains")
    public static void verifyTextInElementContains(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        assertTrue(elm.getText().contains(text));
    }

    @Step("softVerifyNumOfElement")
    public static void softVerifyNumOfElement(List<WebElement> elm, int equals) {
        if (elm.size() != 0) {
            webDriverWait.until(ExpectedConditions.visibilityOf(elm.get(elm.size() - 1)));
        }
        softAssert.assertEquals(elm.size(), equals);
    }

    @Step("verifyNumOfElement")
    public static void verifyNumOfElement(List<WebElement> elm, int equals) {
        System.out.println(elm.toString());
        if (!elm.toString().equals("[]")) {
            System.out.println(elm.toString());
            webDriverWait.until(ExpectedConditions.visibilityOf(elm.get(elm.size() - 1)));
        }
        assertEquals(elm.size(), equals);
    }


    @Step("softVerifyElementIsEnable")
    public static void softVerifyElementIsEnable(WebElement elm) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        softAssert.assertTrue(elm.isEnabled());
    }

    @Step("verifyElementIsEnable")
    public static void verifyElementIsEnable(WebElement elm) {
        assertTrue(webDriverWait.until(ExpectedConditions.invisibilityOf(elm)));
    }

    @Step("verifyElementIsUnEnable")
    public static void verifyElementIsUnEnable(WebElement elm) {
        assertFalse(webDriverWait.until(ExpectedConditions.visibilityOf(elm)).isEnabled());
    }
    @Step("verifyIsBoo")
    public static boolean verifyIsTrue(boolean boo, String value) {
        softAssert.assertTrue(boo, value);
        return boo;
    }

    @Step("softVerifyElementIsUnEnable")
    public static void softVerifyElementIsUnEnable(Boolean boo) {

        softAssert.assertFalse(boo == true);
    }



//    @Step("Verify element visually")
//    public static void visualElement(String expectedImageName){
//        try {
//            screen.find(reedFromXml("ImegeRepo", 0) + expectedImageName  + ".PNG");
//
//        } catch (FindFailed F){
//            System.out.println(F.toString());
//            fail(F.toString());
//        }
}