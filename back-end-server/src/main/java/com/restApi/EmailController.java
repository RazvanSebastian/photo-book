package com.restApi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DTO.EmailMessage;
import com.exception.EmailException;
import com.service.local.EmailService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class EmailController {

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/api/send-email", method = { RequestMethod.GET, RequestMethod.HEAD })
	public ResponseEntity<String> receiveEmailMessage(HttpServletRequest request,@RequestHeader("Email")String email,@RequestHeader("Name") String name, @RequestHeader("Company") String company,
			@RequestHeader("Message") String message) {
		EmailMessage emailMessage=new EmailMessage(email,name,company,message);
		try {
			this.emailService.getInfoAndSendMail(emailMessage,request.getRemoteAddr());
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.TOO_MANY_REQUESTS);
		}
	}
}
