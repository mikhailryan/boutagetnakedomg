package config;

import java.io.UnsupportedEncodingException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final String sender_email = "mikufromsomewhere@gmail.com";
    private static final String password = "sfndygstjowtuaun";
    private static final String senderName = "DropXchange Support";

    public static boolean sendResetCodeEmail(String toEmail, int resetCode) {
        return sendCodeEmail(toEmail, "Password Reset Request", resetCode);
    }

    public static boolean sendVerificationCodeEmail(String toEmail, int verificationCode) {
        return sendCodeEmail(toEmail, "Email Verification", verificationCode);
    }

    private static boolean sendCodeEmail(String toEmail, String subject, int code) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("X-Priority", "1");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender_email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender_email, senderName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setReplyTo(InternetAddress.parse("support@dropxchange.com"));
            message.setSubject(subject);

            // Body content for the email
            String emailBody = "<h2>" + subject + "</h2>"
                    + "<p>Hello,</p>"
                    + "<p>We received a request to " + subject.toLowerCase() + ". Use the code below to proceed:</p>"
                    + "<h3 style='color: #2E86C1;'>" + code + "</h3>"
                    + "<p>If you did not request this, you can ignore this message.</p>"
                    + "<br><p>Thanks,<br>DropXchange Support Team</p>";

            message.setContent(emailBody, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        }
    }
}
