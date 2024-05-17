package SocialMedia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import SocialMedia.Entities.Account;
import SocialMedia.Enums.Role;

@Repository
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
}

package SocialMedia.Repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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
    
    @Query("SELECT COUNT(f) "
    	     + "FROM Friend f "
    	     + "WHERE f.IsAccepted = true "
    	     + "AND (f.friendId.usernameFriend = :username OR f.friendId.usernameYou = :username)")
    Long countFriend(String username);
    
    @Query("SELECT f.friendId.usernameYou FROM Friend f WHERE f.friendId.usernameFriend = :username AND f.IsAccepted = true")
    Set<String> findAcceptedFriendsAsYou(String username);

    @Query("SELECT f.friendId.usernameFriend FROM Friend f WHERE f.friendId.usernameYou = :username AND f.IsAccepted = true")
    Set<String> findAcceptedFriendsAsFriend(String username);
}
