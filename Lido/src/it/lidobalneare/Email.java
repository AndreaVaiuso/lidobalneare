package it.lidobalneare;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    public static void sendRegisterConfirmation(String email) {

        final String username = "noreplylido@gmail.com";
        final String password = "lidobalneare";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplylido@gmail.com"));
            message.setRecipients( Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Confirm registration to Lido");
            
            message.setText("To confirm your registration, please click on this link: ");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}