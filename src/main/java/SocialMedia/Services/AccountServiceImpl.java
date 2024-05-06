package SocialMedia.Services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Repositories.AccountRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements UserDetailsService  {
	
	private AccountRepository accountRepository;
	
	private final static String EMAIL_NOT_FOUND_MSG = "Email %s not found!";

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MSG)));
	}
	
	 public int enableUser(String email) {
	        return accountRepository.enableUser(email);
	 }
	 
	 public Optional<Account> findByEmail(String email) {
		 return accountRepository.findByEmail(email);
	 }
}
