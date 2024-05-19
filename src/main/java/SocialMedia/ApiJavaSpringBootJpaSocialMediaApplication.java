package SocialMedia;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import SocialMedia.Entities.Account;
import SocialMedia.Enums.Role;
import SocialMedia.Repositories.AccountRepository;

@SpringBootApplication
public class ApiJavaSpringBootJpaSocialMediaApplication implements CommandLineRunner{

	@Autowired
	private AccountRepository accountRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiJavaSpringBootJpaSocialMediaApplication.class, args);
	}
	public void run(String... args) {
		Account adminAccount = accountRepository.findByRole(Role.ADMIN);
		if (null == adminAccount) {
			Account account = new Account();
			account.setUsername("admin");
			account.setEmail("caothithuthuy00000@gmail.com");
			account.setAvatarURL("https://drive.google.com/uc?export=view&id=11TZstci09GnRw559j1zbbiKErlJep5Ko");
			account.setFullname("admin");
			account.setRole(Role.ADMIN);
			account.setEnable(true);
			account.setAvatarURL("https://drive.usercontent.google.com/download?id=11TZstci09GnRw559j1zbbiKErlJep5Ko&export=view&authuser=0");
			account.setPassword(new BCryptPasswordEncoder().encode("123456789"));
			Optional<Account> optAccount = accountRepository.findByEmail(account.getEmail());
			if (optAccount.isEmpty()) {
				accountRepository.save(account);
			}
		}
	}
}
