package config;

import java.io.UnsupportedEncodingException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        final String username = "mikufromsomewhere@gmail.com";
        final String password = "sfndygstjowtuaun";

        String receiverAddress = "mikhailryanmuralla@gmail.com";
        String senderName = "DropXchange Support";

        int resetCode = 69420;

        // Gmail SMTP server settings
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");      // Gmail SMTP server
        props.put("mail.smtp.port", "587");                 // TLS port
        props.put("mail.smtp.auth", "true");                // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");     // Secure connection
        props.put("X-Priority", "1");                       // High priority

        javax.mail.Session session = javax.mail.Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            InternetAddress fromAddress = new InternetAddress(username, senderName);
            message.setFrom(fromAddress);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress));
            message.setReplyTo(InternetAddress.parse("support@dropxchange.com"));
            message.setSubject("Password Reset Request");

            String emailBody = "<h2>Password Reset Request</h2>"
                    + "<p>Hello,</p>"
                    + "<p>We received a request to reset your password. Use the code below to proceed:</p>"
                    + "<h3 style='color: #2E86C1;'>" + resetCode + "</h3>"
                    + "<p>If you did not request this, you can ignore this message.</p>"
                    + "<br><p>Thanks,<br>DropXchange Support Team</p>";

            message.setContent(emailBody, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
