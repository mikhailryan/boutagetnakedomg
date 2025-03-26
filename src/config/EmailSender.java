package config;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        // Your Gmail credentials (use your App Password here)
        final String username = "mikhailryanmuralla@gmail.com";
        final String password = "lyqzgvvguxaxfjps";

        // Gmail SMTP server settings
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");      // Gmail SMTP server
        props.put("mail.smtp.port", "587");                 // TLS port
        props.put("mail.smtp.auth", "true");                // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");     // Secure connection
        props.put("mail.debug", "true");
        props.put("mail.smtp.debug", "true");
        

        // Create a mail session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mikhailryanmuralla@gmail.com")); // sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ashbalbz04@gmail.com")); // recepient
            message.setSubject("Urgent");
            message.setText("Greetings, palihug kog screenshot if nadawat nimo then i send nako HAHAHA");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
