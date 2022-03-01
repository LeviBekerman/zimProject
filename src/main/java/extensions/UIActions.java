package extensions;

import Utilities.CommonOps;
import com.google.common.util.concurrent.Uninterruptibles;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class UIActions extends CommonOps {

    @Step("Click on Element")
    public static void click(WebElement elm) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(elm)).click();
    }

    @Step("Update field text")
    public static void updateText(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm)).sendKeys(text);
    }

    @Step("keys Pressing on {valueKeys}")
    public static void keysPressing(WebElement elm, Keys valueKeys) {
        elm.sendKeys(valueKeys);
    }

    @Step("Update field text")
    public static void updateTextDivideIntoSingleLetters(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        for (char ch : text.toCharArray()) {
            elm.sendKeys(ch + "");
            Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
        }
    }

    @Step("Clear field text")
    public static void clearFieldText(WebElement elm) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm)).clear();
    }

    @Step("Update drop down")
    public static void upDateDropDown(WebElement elm, String text) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elm));
        Select dropDown = new Select(elm);
        dropDown.selectByVisibleText(text);
    }

    @Step("Mouse over")
    public static void mouseOver(WebElement elm1, WebElement elm2) {
        action.moveToElement(elm1).moveToElement(elm2).click().perform();
    }

}