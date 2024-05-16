package SocialMedia.Repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import SocialMedia.Entities.Account;
import SocialMedia.Enums.Role;

public interface AccountRepository extends JpaRepository<Account, String> {
	
	Optional<Account> findByUsername(String username);
	
    Optional<Account> findByEmail(String email);
    
    Account findByRole(Role role);
    
    @Query("SELECT a "
			+ "FROM Account a "
			+ "WHERE a.email = :emailOrUsername "
			+ "or a.username = :emailOrUsername")
    Optional<Account> findByEmailOrUsername(String emailOrUsername);
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.enable = TRUE WHERE a.email = ?1")
    int enableUser(String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.password = ?1 " +
            "WHERE a.email = ?2")
    int updatePassword(String newPassword, String email);
}
