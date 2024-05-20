package SocialMedia.Auth.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private final String fromEmail = "147.qter@gmail.com";
	private final String senderName = "Social Media PIE";

	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setText(email, true);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			LOGGER.error("Failed to send email!", e);
			throw new IllegalStateException(("Failed to send email!"));
		}
	}

	@Override
	@Async
	public void sendEmail(String toEmail, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			var messageHelper = new MimeMessageHelper(message, "utf-8");

			messageHelper.setFrom(fromEmail, senderName);
			messageHelper.setTo(toEmail);
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);

			mailSender.send(message);
		} catch (Exception e) {
			LOGGER.error("Failed to send email!", e);
			throw new IllegalStateException(("Failed to send email!"));
		}

	}
}
