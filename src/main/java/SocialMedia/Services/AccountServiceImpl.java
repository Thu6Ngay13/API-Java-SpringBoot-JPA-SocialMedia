package SocialMedia.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Repositories.AccountRepository;

@Service
public class AccountServiceImpl implements IAccountService, UserDetailsService {
	private final static String EMAIL_NOT_FOUND_MSG = "Email %s not found!";

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
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
	
	@Override
	public int enableUser(String email) {
		return accountRepository.enableUser(email);
	}

	@Override
	public int updatePassword(String newPassword, String email) {
		return accountRepository.updatePassword(newPassword, email);
	}
	
	
	@Override
	public <S extends Account> S save(S entity) {
		return accountRepository.save(entity);
	}

	@Override
	public long countFriend(String username) {
		return accountRepository.countFriend(username);
	}

	@Override
	public Set<String> getAcceptedFriends(String username) {
        Set<String> friendsAsYou = accountRepository.findAcceptedFriendsAsYou(username);
        Set<String> friendsAsFriend = accountRepository.findAcceptedFriendsAsFriend(username);

        Set<String> allAcceptedFriends = new HashSet<>();
        allAcceptedFriends.addAll(friendsAsYou);
        allAcceptedFriends.addAll(friendsAsFriend);

        return allAcceptedFriends;
    }

	@Override
	public int updateProfile(String fullname, String gender, String description, String company, String location,
			boolean isSingle, String username) {
		return accountRepository.updateProfile(fullname, gender, description, company, location, isSingle, username);
	}

	@Override
	public int updateAvatar(String username, String avatarURL) {
		return accountRepository.updateAvatar(username, avatarURL);
	}
}
