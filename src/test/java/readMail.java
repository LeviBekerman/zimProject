import extensions.Mail.EmailReceiver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(Utilities.Listeners.class)
public class readMail extends Utilities.CommonOps {

    @Test
    public static void readMail(String userName, String password, String validation, int resultLimit) {
        EmailReceiver receiver = new EmailReceiver();
        receiver.downloadEmails(userName, password, validation, resultLimit);
    }
}
