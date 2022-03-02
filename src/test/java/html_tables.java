import io.qameta.allure.Step;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

public class html_tables extends pageObjects.html_tables {
    @Test
    @Step("Read specific mail and limit results")
    public static void veifyValuesFromExel() throws Exception {
        ArrayList<Map<String, String>> ARRreadFromExel = extensions.readFromExcel.readFromExcelFunction("C:\\Automation\\ZIMJobInterview\\testData\\html_tables.xlsx", extensions.readFromExcel.getFieldsName("C:\\Automation\\ZIMJobInterview\\testData\\html_tables.xlsx").size() - 1);
        for (int a = 0; a< ARRreadFromExel.size() - 1;a++) {

            /*
             * WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText
             */
            verifyTableCellText(elm_table, Integer.parseInt(ARRreadFromExel.get(a).get("searchColumn")), ARRreadFromExel.get(a).get("searchText"),
                    Integer.parseInt(ARRreadFromExel.get(a).get("returnColumnText")), ARRreadFromExel.get(a).get("expectedText"));
        }
        softAssert.assertAll();

    }

}
