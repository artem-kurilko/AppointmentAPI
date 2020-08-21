package com.appointment.notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotification {

    public void sendMail(String recipientName, String subject, String text) throws Exception {
        final String SENDER_NAME = "yourmail@gmail.com";
        final String SENDER_PASSWORD = "youremailpassword";

        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port", 465);
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", 465);

        Session s = Session.getInstance(p,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(SENDER_NAME,SENDER_PASSWORD);
                    }
                }
        );

        Message message = new MimeMessage(s);
        try {
            message.setFrom(new InternetAddress(SENDER_NAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientName));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Сообщение было успешно отправлено.");
        } catch (MessagingException e) {
            throw new MessagingException("Сообщение не было отправлено. " + e);
        }
    }
}