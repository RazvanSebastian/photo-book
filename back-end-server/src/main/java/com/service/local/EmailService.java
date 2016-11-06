package com.service.local;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.entity.DTO.EmailMessage;
import com.exception.EmailException;
import com.repository.model.IpAdress;
import com.repository.repository.IpAdressRepository;

@Service
public class EmailService
{
	private MailSender mailSender;
	
	/**
	 * @param getTime() is in milliseconds since 1970 so we need to plus with ONE_DAY to have one day delay
	 *  
	 * */
	private static final int ONE_DAY=1000*60*60*24;
	
	@Autowired
	private IpAdressRepository repo;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	private void createAndSendEmail(EmailMessage emailMessage){
		String message="New message from : "+emailMessage.getName()+'\n'+
				"Company : "+emailMessage.getCompany()+" ,email : "+emailMessage.getEmail()+'\n'+
				"Message : "+'\n'+
				emailMessage.getMessage();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");

		EmailService mm = (EmailService) context.getBean("mailMail");
		mm.sendMail("photo.book.api@gmail.com", "rzvs95@gmail.com", "Photo Book", message);
	}
	
	public void getInfoAndSendMail(EmailMessage emailMessage,String ipAdress) throws EmailException{
		IpAdress ipRequest=new IpAdress();
		String date=new Date().getTime()+"";
		ipRequest=repo.findByIp(ipAdress);
		if(repo.findByIp(ipAdress)==null){
			repo.save(new IpAdress(ipAdress,date,1));
			this.createAndSendEmail(emailMessage);
		}
			else{
			if(Long.parseLong(ipRequest.getDate())+this.ONE_DAY>=Long.parseLong(date)){
				if(ipRequest.getNumb()<3)
					this.repo.setNumbByIp(ipAdress,ipRequest.getNumb()+1);
				else
					throw new EmailException("You have been send 3 emails from this ip! We are waiting tomorow to share with us your ideas!");
			}
			else{
				//if we have less then 3 email send today(last 24 hours) we have permission to send email
				this.repo.setDateAndNumbByIp(ipAdress, date, 1);
				this.createAndSendEmail(emailMessage);
			}
		}
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
