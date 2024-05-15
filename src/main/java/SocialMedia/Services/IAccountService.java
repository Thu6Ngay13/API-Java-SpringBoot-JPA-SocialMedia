package SocialMedia.Services;

import java.util.Optional;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;

public interface IAccountService {
	Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
	<S extends Account> S save(S entity);
}
