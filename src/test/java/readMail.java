import extensions.Mail.EmailReceiver;
import io.qameta.allure.Step;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


@Listeners(Utilities.Listeners.class)
public class readMail extends Utilities.CommonOps {

    @Test
    // Put coma between validation value for example( validation = "Vayman Barak,Log-On Software")
    @Step("Read specific mail and limit results")
    @Parameters( {"userName","password","validation","resultLimit"})
    public static void readMail(String userName, String password, String validation, int resultLimit) {
        EmailReceiver receiver = new EmailReceiver();
        receiver.downloadEmails(userName, password, validation, resultLimit);
    }
}
