package Utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Listeners(Utilities.Listeners.class)
public class CommonOps extends Base {


    @BeforeTest
    public static void startSessions(String platformName, String UDID, String APP_PACKAGE, String APP_ACTIVITY) {
        initBrowser(reedFromXml("browser", 0));
        softAssert = new SoftAssert();
    }








    @BeforeMethod
    public static void beforeMethod (Method method){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        str_testName = method.getName() + "_" + now.toString().replace("-", "_").replace(":", "_").replace(".", "_");
        if (!platform.toUpperCase(Locale.ROOT).equals("RESTAPI")) {
            try {
                MonteScreenRecorder.startRecord(str_testName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AfterTest
    public static void afterSessions () {
        //ManageDB.closeConnection();
        if (!platform.equalsIgnoreCase("RESTAPI")) {
            if (platform.equalsIgnoreCase("WEB")){
                driver.quit();
            }
        } else {

        }
    }

    public static void initBrowser (String browser){

        if (browser.toLowerCase(Locale.ROOT).equals("chrome")) {
            driver = initChromeDriver();
        } else if (browser.toLowerCase(Locale.ROOT).equals("firefox")) {
            driver = initFireFoxDriver();
        } else if (browser.toLowerCase(Locale.ROOT).equals("ie")) {
            driver = initIeDriver();
        } else throw new RuntimeException("Invalid browser");

        System.out.println("after init");
        driver.manage().timeouts().implicitlyWait(Long.parseLong(reedFromXml("timeOut", 0)), TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(driver, Long.parseLong((reedFromXml("timeOut", 0))));
        System.out.println(reedFromXml("url", 0));
        driver.get(reedFromXml("url", 0) + " !!!after url");
        driver.manage().window().maximize();
        //ManagePages.initWeb();
        action = new Actions(driver);
        // WebFlows.log(reedFromXml("UserName", 0), reedFromXml("Password", 0));
        //WebFlows.log("admin", "admin");

    }

    public static WebDriver initChromeDriver () {
        WebDriverManager.chromedriver().setup();
        return driver = new ChromeDriver();
    }

    public static WebDriver initFireFoxDriver () {
        WebDriverManager.firefoxdriver().setup();
        return driver = new FirefoxDriver();
    }

    public static WebDriver initIeDriver () {
        WebDriverManager.iedriver().setup();
        return driver = new InternetExplorerDriver();
    }



    public static String reedFromXml (String value,int index){
        DocumentBuilder documentBuilder;
        Document doc = null;
        File xml = new File("./Configuration/DataConfig.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(xml);

        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(value).item(index).getTextContent();
    }


}

