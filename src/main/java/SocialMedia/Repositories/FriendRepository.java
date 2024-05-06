package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
	@Query("SELECT a FROM Account a JOIN Friend f ON a.username = f.friendId.usernameYou WHERE f.friendId.usernameFriend = :username AND f.IsAccepted = true")
    List<Account> findAllYourFriends(String username);
	
	@Query("SELECT a FROM Account a JOIN Friend f ON a.username = f.friendId.usernameYou WHERE f.friendId.usernameFriend = :username AND f.IsAccepted = false")
    List<Account> findAllFriendRequests(String username);
}
