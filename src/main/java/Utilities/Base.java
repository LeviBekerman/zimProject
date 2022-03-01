package Utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.WebDriverWait;




public class Base {
    // Web
    protected static WebDriver driver;

    // General
    protected static WebDriverWait webDriverWait;
    protected static Actions action;
    protected static SoftAssert softAssert;
    protected static String str_testName;
    protected static String platform = "";


    // Page object -Web
 //   protected static LoginPage grafanaLogin;


}