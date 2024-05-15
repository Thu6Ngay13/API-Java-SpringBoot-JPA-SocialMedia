package SocialMedia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;
import SocialMedia.Repositories.AccountRepository;

@Service
public class AccountServiceImpl implements IAccountService, UserDetailsService {
	private final static String EMAIL_NOT_FOUND_MSG = "Email %s not found!";

	@Autowired
	AccountRepository accountRepository;
	
	public Optional<Account> findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Optional<Account> findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
		return accountRepository.findByEmailOrUsername(emailOrUsername)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MSG)));
	}
	public int enableUser(String email) {
		return accountRepository.enableUser(email);
	}
	
	@Override
	public <S extends Account> S save(S entity) {
		return accountRepository.save(entity);
	}
}
