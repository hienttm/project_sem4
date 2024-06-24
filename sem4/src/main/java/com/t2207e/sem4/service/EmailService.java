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
        String body = "Hãy click vào đường link để gửi đặt lại mật khẩu: http://localhost:8089/resetPassworUrl/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendEmailConfirmAccount(String to, String token) {
        String subject = "CONFIRM YOUR ACCOUNT";
        String body = "Hãy click vào đường link để xác minh tài khoản của bạn: http://localhost:8089/confirmAccount/" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendMailNotiRegisterTeacherStatus(String to){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo");
        message.setText("Chúng tôi đã nhận được yêu cầu đăng ký trở thành giáo viên của bạn. Vui lòng chờ kết quả đăng ký. Chúng tôi sẽ phản hồi qua Email này trong 2 ngày làm việc.");
        ;

        mailSender.send(message);
    }
    public void sendMailNotiContactUsStatus(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo");
        message.setText("Chúng tôi đã nhận được tin nhắn từ bạn. Chúng tôi sẽ phản hồi qua Email này trong 2 ngày làm việc.");
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
