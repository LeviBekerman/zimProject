package extensions.Mail;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This program demonstrates how to get e-mail messages from a POP3/IMAP server
 *
 * @author www.codejava.net
 */
public class EmailReceiver {
    /**
     * }
     * Returns a Properties object which is configured for a POP3/IMAP server
     *
     * @param protocol either "imap" or "pop3"
     * @param host
     * @param port
     * @return a Properties object
     */
    private Properties getServerProperties(String protocol, String host,
                                           String port) {
        Properties properties = new Properties();

        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);

        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));

        return properties;
    }

    /**
     * Downloads new messages and fetches details for each message.
     *
     * @param condition
     * @param userName
     * @param password
     * @param resultsLimit
     */
    public int downloadEmails(
            String userName, String password, String condition, int resultsLimit) {

        // for POP3
        //String protocol = "pop3";
        //String host = "pop.gmail.com";
        //String port = "995";

        // for IMAP
        String protocol = "imap";
        String host = "imap.gmail.com";
        String port = "993";
        String[] SPLcondition = condition.split(",");
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        int resultsFlag = 0;
        try {
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(userName, password);


            /////////////////////////////////
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            ArrayList<Message[]> arrMessage = new ArrayList<>();

            Message[] messages = folderInbox.getMessages();
            arrMessage.add(messages);

            folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.HOLDS_MESSAGES);
            messages = folderInbox.getMessages();
            arrMessage.add(messages);

            folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);
            messages = folderInbox.getMessages();
            arrMessage.add(messages);

            folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.HOLDS_FOLDERS);
            messages = folderInbox.getMessages();
            arrMessage.add(messages);


            // opens the inbox folder


            for (int x = arrMessage.size() - 1; x > 0; x--) {
                for (int i = messages.length - 1; i > 0; i--) {
                    boolean isValid = false;

                    Message msg = arrMessage.get(x)[i];

                    Address[] fromAddress = msg.getFrom();
                    String from = fromAddress[0].toString();
                    String subject = msg.getSubject();
                    String toList = parseAddresses(msg
                            .getRecipients(RecipientType.TO));
                    String ccList = parseAddresses(msg
                            .getRecipients(RecipientType.CC));
                    String sentDate = msg.getSentDate().toString();
                    String contentType = msg.getContentType();
                    String messageContent = "";

                    Message msgGtMessageCount = folderInbox.getMessage(folderInbox.getMessageCount());
                    Address[] in = msgGtMessageCount.getFrom();

                    if (contentType.toLowerCase().contains("text/plain")
                            || contentType.toLowerCase().contains("text/html")) {
                        try {
                            Object content = msg.getContent();
                            if (content != null) {
                                String plainText = Jsoup.parse(content.toString()).wholeText();

                                messageContent = plainText;

                            }
                        } catch (Exception ex) {
                            messageContent = "[Error downloading content]";
                            ex.printStackTrace();
                        }
                    } else {
                        Multipart mp;
                        BodyPart bp;
                        try {
                            mp = (Multipart) msg.getContent();
                            bp = mp.getBodyPart(0);
                            Object content = bp.getContent();
                            String plainText = Jsoup.parse(content.toString()).text();
                            if (content != null) {
                                plainText = Jsoup.parse(content.toString()).wholeText();

                                messageContent = plainText;

                            }
                        } catch (Exception d) {
                            System.out.println(d.toString());
                        }
                    }


                    // Check isValid == false
                    isValid = isContains(SPLcondition, Integer.toString(i));

                    if (isValid == false)
                        isValid = isContains(SPLcondition, from);

                    if (isValid == false) {
                        toList = "" + toList;
                        isValid = isContains(SPLcondition, toList);
                    }
                    if (isValid == false) {
                        ccList = "" + ccList;
                        isValid = isContains(SPLcondition, ccList);
                    }
                    if (isValid == false) {
                        subject = "" + subject;
                        isValid = isContains(SPLcondition, subject);
                    }
                    if (isValid == false) {
                        isValid = isContains(SPLcondition, messageContent);
                    }
                    // Print only isValid == true
                    if (isValid == true) {
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        resultsFlag++;
                        System.out.println("\t Sent Date: " + sentDate);
                        // print out details of each message
                        System.out.println("Message #" + (i + 1) + ":");
                        System.out.println("\t From: " + from);
                        System.out.println("\t To: " + toList);
                        System.out.println("\t CC: " + ccList);
                        System.out.println("\t Subject: " + subject);
                        System.out.println("\t Sent Date: " + sentDate);
                        System.out.println("\t Message: " + messageContent);
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        if (resultsFlag == resultsLimit) {
                            break;
                        }
                    }

                }
                if (resultsFlag == resultsLimit) {
                    break;
                }
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (
                NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (
                MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
return resultsFlag;
    }

    /**
     * Returns a list of addresses in String format separated by comma
     *
     * @param address an array of Address objects
     * @return a string represents a list of addresses
     */
    private String parseAddresses(Address[] address) {
        String listAddress = "";

        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress += address[i].toString() + ", ";
            }
        }
        if (listAddress.length() > 1) {
            listAddress = listAddress.substring(0, listAddress.length() - 2);
        }

        return listAddress;
    }

    public static Boolean isContains(String[] value, String STRvalue) {
        boolean isTrue = false;
        for (int i = 0; i < value.length; i++) {
            if (STRvalue.contains(value[i])) {
                isTrue = true;
                break;
            }
        }
        return isTrue;

    }

}