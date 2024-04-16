package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	MailSender mailSender;
	
	public void sendMail(String mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        // 送信元メールアドレス
        msg.setFrom(mail);

       // 送信先メールアドレス
        msg.setTo(mail);
        
        // タイトル 
        msg.setSubject("新規登録");               
  
        //本文
        msg.setText("登録ありがとうございます\r\nスタッフ一同心から喜び申し上げます"); 

        try {
            mailSender.send(msg);
        } catch (MailException e) {
            e.printStackTrace();
        }
	}
}