package com.appointment.notification;

import com.appointment.domain.EmailType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotification {

    public void sendMail(EmailType type, String RECIPIENT_NAME) throws Exception {
        final String SENDER_NAME = "kurilko365@gmail.com";
//                "universityappointmentapi@gmail.com";
        final String SENDER_PASSWORD = "A123tmp123key";

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
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT_NAME));
            String subject;
            String text;

            switch (type){
                case REGISTERED:
                    subject = "Регистрация пользователя";
                    text = "Пользователь был успешно зарегестрирован.";
                    break;

                case RESERVED:
                    subject = "Уведомление о резервации";
                    text = "Студент зарезервировал у вас время, пожалуйста отмените или подтвердите резервацию.";
                    break;

                case DECLINED:
                    subject = "Уведомление о резервации";
                    text = "Преподователь отклонил вашу резервацию. Пожалуйста отмените её в вашем расписании.";
                    break;

                default:
                    throw new Exception("Email type not found exception");
            }

            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Сообщение было успешно отправлено.");
        } catch (MessagingException e) {
            throw new MessagingException("Сообщение не было отправлено. " + e);
        }
    }
}