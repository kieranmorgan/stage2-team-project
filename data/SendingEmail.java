package com.example.teamproject.data;

import com.example.teamproject.Globals;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Kieran Morgan
 * sends a confirmation email for 2FA and for account numbers
 */
public class SendingEmail {
    /**
     *
     * @param email  takes the email address given and sends a confirmation code
     *
     */
    public static void send2faEmail(String email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("csc2033team27@gmail.com", "CSC2033Team27");
            }
        });
        Globals glob = new Globals();
        glob.setCode();
        String msg = "<strong>This is your confirmation code:   </strong>" + Globals.userCode;
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("csc2033team27@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("StubankPLC Confirmation Code");
            message.setContent(msg, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("message sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param email takes the email address and generated customerID and sends information in email
     * @param accountNumber
     */
    public static void sendCustIDEmail(String email, String accountNumber) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("csc2033team27@gmail.com", "CSC2033Team27");
            }
        });
        Globals glob = new Globals();
        glob.setCode();
        String msg = "<strong>This is your account number:   </strong>" + accountNumber;
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("csc2033team27@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("StubankPLC Account Number");
            message.setContent(msg, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("message sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
