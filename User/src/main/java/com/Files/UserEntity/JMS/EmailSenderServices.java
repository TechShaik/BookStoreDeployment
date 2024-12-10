package com.Files.UserEntity.JMS;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Files.UserEntity.User;

@Component
@Service
public class EmailSenderServices  {

	@Autowired
	private JavaMailSender MailSender;



	public void sendMail(String toEmail,String subject,String body,User user) {

		SimpleMailMessage simpleMessage=new SimpleMailMessage();
		 
		simpleMessage.setTo(toEmail);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(body);

		MailSender.send(simpleMessage);

		System.out.println("Mail sent successfully.......");

	}
}
