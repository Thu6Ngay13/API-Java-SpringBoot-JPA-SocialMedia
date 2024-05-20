package SocialMedia.Auth.Email;

public interface EmailSender {
	void send(String to, String email);
	void sendEmail(String toEmail, String subject, String body);
}
