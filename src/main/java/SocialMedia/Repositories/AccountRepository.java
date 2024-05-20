package SocialMedia.Repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.SocialGroup;
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
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a " +
            "SET a.password = ?1 " +
            "WHERE a.email = ?2")
    int updatePassword(String newPassword, String email);
    
    @Query("SELECT COUNT(f) "
    	     + "FROM Friend f "
    	     + "WHERE f.isAccepted = true "
    	     + "AND (f.friendId.usernameFriend = :username OR f.friendId.usernameYou = :username)")
    Long countFriend(String username);
    
    @Query("SELECT f.friendId.usernameYou FROM Friend f WHERE f.friendId.usernameFriend = :username AND f.isAccepted = true")
    Set<String> findAcceptedFriendsAsYou(String username);

    @Query("SELECT f.friendId.usernameFriend FROM Friend f WHERE f.friendId.usernameYou = :username AND f.isAccepted = true")
    Set<String> findAcceptedFriendsAsFriend(String username);
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.fullname = ?1, a.gender = ?2, a.description = ?3, a.company = ?4, a.location = ?5, a.isSingle = ?6 WHERE a.username = ?7")
    int updateProfile(String fullname, String gender, String description, String company, String location, boolean isSingle, String username);
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.avatarURL = ?2 WHERE a.username = ?1")
    int updateAvatar(String username, String avatarURL);
}
