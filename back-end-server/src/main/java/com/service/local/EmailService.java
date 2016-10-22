package com.service.local;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.entity.DTO.EmailMessage;

@Service
public class EmailService
{
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void getInfoAndSendMail(EmailMessage emailMessage){
		String message="New message from : "+emailMessage.getName()+'\n'+
				"Company : "+emailMessage.getCompany()+" ,email : "+emailMessage.getEmail()+'\n'+
				"Message : "+'\n'+
				emailMessage.getMessage();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");

		EmailService mm = (EmailService) context.getBean("mailMail");
		mm.sendMail("photo.book.api@gmail.com", "rzvs95@gmail.com", "Photo Book", message);
	}

	private void sendMail(String from, String to, String subject, String msg) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
}
