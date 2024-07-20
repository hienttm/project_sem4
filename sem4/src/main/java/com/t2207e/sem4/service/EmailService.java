package com.t2207e.sem4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(String to, String token) {
        String subject = "RESET PASSWORD";
        String body = "Please click on the link to reset your password: http://localhost:8089/resetPassworUrl/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendEmailConfirmAccount(String to, String token) {
        String subject = "CONFIRM YOUR ACCOUNT";
        String body = "Please click on the link to verify your account: http://localhost:8089/confirmAccount/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendMailNotiConfirmAccountSuccess(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your account has been successfully authenticated");
        message.setText("Your account has been successfully authenticated. Wellcome to Online Courses!");

        mailSender.send(message);
    }
    public void sendMailNotiRegisterTeacherStatus(String to){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Notification");
        message.setText("We have received your request to become a teacher. Please wait for registration results. We will respond via this Email within 2 working days.");
        ;

        mailSender.send(message);
    }
    public void sendMailNotiContactUsStatus(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Notification");
        message.setText("We have received your message. We will respond via this Email within 2 working days.");
        ;

        mailSender.send(message);

    }
    public void getMailNotiContactEmail(String email, String messageFromUser,String name){
        String body="Tên khách hàng: " + name +", email: " +email+", nội dung tin nhắn: "+messageFromUser;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hienttm.aof@gmail.com");
        message.setSubject("Tin nhắn Contact Us");
        message.setText(body);

        mailSender.send(message);
    }
}
