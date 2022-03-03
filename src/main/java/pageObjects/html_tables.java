package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class html_tables extends  extensions.UIActions{

    @FindBy(xpath = "//table[@id='customers']/tbody")
    public static WebElement elm_table;

}
