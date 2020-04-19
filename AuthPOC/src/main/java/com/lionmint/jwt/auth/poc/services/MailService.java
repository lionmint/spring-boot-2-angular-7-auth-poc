package com.lionmint.jwt.auth.poc.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lionmint.jwt.auth.poc.controller.SignUpController;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class MailService {

	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);
	
	private static final Email from = new Email("no-reply@lionmint.com");
	private static String subject;
	private static String url;
	private static String content;
	
	
	public static void SendMail(String receiver, Content content, String subject, String sendgridKey) throws IOException
	{	
		final Email to = new Email(receiver);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid(sendgridKey);
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	    } catch (IOException ex) {
	      throw ex;
	    }
	}
	
	public static void SendRegistrationConfirmationMail(String urlParameter, String email, String sendgridKey, String origin)
	{
		subject = "Welcome to Your Website! Please confirm your registration.";
		
		url = "/signup/auth/" + urlParameter;
		
		content = "You have registered yourself on your Website. Your account needs to be activated by clicking the confirmation link. \n\n";
		content += "Link: "+ origin + "" + url + " \n\n";
	    content += "If you have not created an account please ignore this mail.";
 
	    final Content contentEntity = new Content("text/plain", content);
		try {
			SendMail(email, contentEntity, subject, sendgridKey);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}
	
	public static void SendPasswordChangeConfirmationMail(String email, String sendgridKey, String origin, String urlParameter)
	{
		subject = "You have requested a change of your Password! ";
		
		url = "/password/" + urlParameter;
		
		content = "A request to change the password has been sent. Please execute the link in this mail and define your new Password.\n\n";
		content += "Link: " + origin + "" + url + " \n\n";
	    content += "If you have not requested this change, please ignore this mail.";
 
	    final Content contentEntity = new Content("text/plain", content);
		try {
			SendMail(email, contentEntity, subject, sendgridKey);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}
}

