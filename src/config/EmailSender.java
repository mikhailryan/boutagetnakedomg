package config;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class EmailSender {
    private static final String apiKey;
    static {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties.txt"));
            apiKey = props.getProperty("apiKey");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API Key", e);
        }
    }
    
    private static final String senderEmail = "mikumikucarambinu@gmail.com";
    private static final String senderName = "DropXchange Support";

    public static boolean sendResetCodeEmail(String toEmail, int resetCode) {
        return sendCodeEmail(toEmail, "Password Reset Request", resetCode);
    }

    public static boolean sendVerificationCodeEmail(String toEmail, int verificationCode) {
        return sendCodeEmail(toEmail, "Email Verification", verificationCode);
    }

    private static boolean sendCodeEmail(String toEmail, String subject, int code) {
        String body = "{"
                + "\"sender\": { \"name\": \"" + senderName + "\", \"email\": \"" + senderEmail + "\" },"
                + "\"to\": [ { \"email\": \"" + toEmail + "\", \"name\": \"User\" } ],"
                + "\"subject\": \"" + subject + "\","
                + "\"htmlContent\": \"<h2>" + subject + "</h2><p>Hello,</p>"
                + "<p>We received a request to " + subject.toLowerCase() + ". Use the code below to proceed:</p>"
                + "<h3 style='color: #2E86C1;'>" + code + "</h3>"
                + "<p>If you did not request this, you can ignore this message.</p>"
                + "<br><p>Thanks,<br>DropXchange Support Team</p>\""
                + "}";

        try {
            URL url = new URL("https://api.brevo.com/v3/smtp/email");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("api-key", apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Send the JSON body in the POST request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Email sent successfully!");
                return true;
            } else {
                System.out.println("Error sending email. Response code: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
