package beans;

import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import config.Config;
import other.ConnectToDatabase;

@Stateless
public class BeanEmail extends ConnectToDatabase implements BeanEmailRemote {
	/**
	 * metoda, ktora odosle email na adresu v argumente a ako var symbol vezme ID zakaznika
	 */

	public void SentEmail(String email, String text) {
		final String username = Config.config.getString("Gmail");
		final String password = Config.config.getString("GmailPassword");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Config.config.getString("Gmail")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Rent Bike with Bike&Go!");
			text = "Thank you for order\n" + text + "Please make payment on:\n\n"
					+ Config.config.getString("BankNumber");
			message.setText(text);
			Transport.send(message);
		}

		catch (MessagingException e) {
			System.out.println(e);
		}
	}

}