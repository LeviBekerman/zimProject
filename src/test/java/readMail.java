import Mail.EmailReceiver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Utilities.Listeners.class)
public class readMail {

    @Test
    public static void readMail() {
        String userName = "levitzchake@gmail.com";
        String password = "0525234109levi";

        // Coma separate validation
        String validation = "Vayman Barak,Log-On Software";
        int resultLimit = 2;
        EmailReceiver receiver = new EmailReceiver();
        receiver.downloadEmails(userName, password, validation, resultLimit);
    }
}
