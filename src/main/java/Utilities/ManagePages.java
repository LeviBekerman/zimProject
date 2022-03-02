package Utilities;

import org.openqa.selenium.support.PageFactory;
import pageObjects.html_tables;

public class ManagePages extends Base{
    public static void initWeb(){
        html_tables = PageFactory.initElements(driver, pageObjects.html_tables.class);
    }
}
