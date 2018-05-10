package model;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import main.Main;

public class Email {
	
	public Email() {
		
	}
	
	public Email(String email, String text) throws AddressException, MessagingException {
		Main.getBe().SentEmail(email, text);
	}
	
}