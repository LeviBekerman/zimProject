package Utilities;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.Locale;

public class Listeners extends CommonOps implements ITestListener {


    public void onFinish(ITestContext arg0)
    {
        System.out.println("-----------Ending Executions------------");
    }

    public void onStart(ITestContext arg0)
    {
        System.out.println("-----------Starting Executions------------");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0)
    {
        System.out.println("onTestFailedButWithinSuccessPercentage");
    }

    public void onTestFailure(ITestResult test)
    {
        System.out.println("---------test name:" + test.getName() + " fail-----------");
            saveScreenShot();
            try {
                MonteScreenRecorder.stopRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public void onTestSkipped(ITestResult teat)
    {
        System.out.println("---------test name:" + teat.getName() + " skipping-----------");
    }

    public void onTestStart(ITestResult test)
    {
        System.out.println("---------test name:" + test.getName() + " starting-----------");

    }

    public void onTestSuccess(ITestResult test)
    {
        System.out.println("---------test name:" + test.getName() + " pass-----------");

            try {
                MonteScreenRecorder.stopRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
            File file = new File("./test-recordings/" + str_testName + ".avi");

            if (file.delete()) {
                System.out.println("delete successfully");
            } else {
                System.out.println("delete fail");
            }
        }


    @Attachment(value = "screen shot", type = "image")
    public static byte[] saveScreenShot()
    {
        return  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);

    }
}
