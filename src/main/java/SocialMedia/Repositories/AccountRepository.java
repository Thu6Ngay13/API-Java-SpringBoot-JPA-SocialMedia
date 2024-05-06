package SocialMedia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import SocialMedia.Entities.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
	
	public Optional<Account> findByUsername(String username);
	
    public Optional<Account> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.enable = TRUE WHERE a.email = ?1")
    int enableUser(String email);
}
