package SocialMedia.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import SocialMedia.Entities.Account;

public interface IAccountService {

	Optional<Account> findByUsername(String username);

	Optional<Account> findByEmail(String email);

	int updatePassword(String newPassword, String email);

	<S extends Account> S save(S entity);

	long countFriend(String username);

	Set<String> getAcceptedFriends(String username);

	int updateProfile(String fullname, String gender, String description, String company, String location,
			boolean isSingle, String username);

	List<Account> findAll();

	int enableUser(String email);
	
	int updateAvatar(String username, String avatarURL);
}
