package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE "
				+ "(f.friendId.usernameYou LIKE :username OR f.friendId.usernameFriend LIKE :username) "
				+ "AND a.username NOT LIKE :username "
				+ "AND f.IsAccepted = true")
    List<Account> findAllYourFriends(@Param("username") String username);
	
	@Query("SELECT a, f.requestTimeAt FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE "
				+ "(f.friendId.usernameYou LIKE :username OR f.friendId.usernameFriend LIKE :username) "
				+ "AND a.username NOT LIKE :username "
				+ "AND f.IsAccepted = false "
			+ "ORDER BY f.requestTimeAt DESC")
	List<Object[]> findAllFriendRequests(@Param("username") String username);
	
	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE "
				+ "a.username LIKE :username2 "
				+ "AND (f.friendId.usernameYou LIKE :username1 OR f.friendId.usernameFriend LIKE :username1)"
				+ "AND f.IsAccepted = false ")
	Optional<Account> findFriendRequestWith2To1(@Param("username1") String username1, @Param("username2") String username2);
	
	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE "
				+ "a.username LIKE :username2 "
				+ "AND (f.friendId.usernameYou LIKE :username1 OR f.friendId.usernameFriend LIKE :username1)"
				+ "AND f.IsAccepted = true ")
	Optional<Account> checkIsFriend(@Param("username1") String username1, @Param("username2") String username2);
	
	
}
