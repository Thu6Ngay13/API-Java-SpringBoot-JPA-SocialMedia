package SocialMedia.Services;

import java.util.Optional;

import SocialMedia.Entities.Account;

public interface IAccountService {
	Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
}
