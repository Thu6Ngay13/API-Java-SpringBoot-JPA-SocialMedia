package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE " + "(f.friendId.usernameYou LIKE :username OR f.friendId.usernameFriend LIKE :username) "
			+ "AND a.username NOT LIKE :username " + "AND f.isAccepted = true")
	List<Account> findAllYourFriends(@Param("username") String username);

	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE " 
			+ "(f.friendId.usernameYou LIKE :username OR f.friendId.usernameFriend LIKE :username) "
				+ "AND a.username NOT LIKE :username " 
				+ "AND f.isAccepted = true " 
				+ "AND a.fullname LIKE %:keyword%")
	List<Account> searchAllYourFriends(@Param("username") String username, @Param("keyword") String keyword);

	@Query("SELECT a, f.requestTimeAt FROM Account a " 
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou "
			+ "WHERE " + "f.friendId.usernameFriend LIKE :username " + "AND f.isAccepted = false "
			+ "ORDER BY f.requestTimeAt DESC")
	List<Object[]> findAllFriendRequests(@Param("username") String username);

	@Query("SELECT a, f.requestTimeAt FROM Account a " + "JOIN Friend f ON a.username LIKE f.friendId.usernameYou "
			+ "WHERE " + "f.friendId.usernameFriend LIKE :username " + "AND f.isAccepted = false "
			+ "AND a.fullname LIKE %:keyword% " + "ORDER BY f.requestTimeAt DESC")
	List<Object[]> searchAllFriendRequests(@Param("username") String username, @Param("keyword") String keyword);

	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE " + "a.username LIKE :username2 "
			+ "AND (f.friendId.usernameYou LIKE :username1 OR f.friendId.usernameFriend LIKE :username1)"
			+ "AND f.isAccepted = false ")
	Optional<Account> findFriendRequestWith2To1(@Param("username1") String username1,
			@Param("username2") String username2);

	@Query("SELECT a FROM Account a "
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameYou OR a.username LIKE f.friendId.usernameFriend "
			+ "WHERE " + "a.username LIKE :username2 "
			+ "AND (f.friendId.usernameYou LIKE :username1 OR f.friendId.usernameFriend LIKE :username1)"
			+ "AND f.isAccepted = true ")
	Optional<Account> checkIsFriend(@Param("username1") String username1, @Param("username2") String username2);

	@Query("SELECT a " 
			+ "FROM Account a " 
			+ "WHERE a.username != :username " 
			+ "AND a.username NOT IN "
				+ "(SELECT f.friendId.usernameFriend " 
				+ "FROM Friend f " 
				+ "WHERE f.friendId.usernameYou = :username " 
					+ "AND (f.isAccepted = true OR f.isAccepted = false)) " 
			+ "AND a.username NOT IN "
				+ "( SELECT f.friendId.usernameYou " 
				+ "FROM Friend f " 
				+ "WHERE f.friendId.usernameFriend = :username " 
				+ "AND (f.isAccepted = true OR f.isAccepted = false)) "
			+ "AND a.fullname LIKE %:keyword% "
			+ "AND a.username NOT LIKE 'admin'")
	List<Account> searchNotFriend(@Param("username") String username, @Param("keyword") String keyword);

	@Query("SELECT a, f.requestTimeAt FROM Account a " 
			+ "JOIN Friend f ON a.username LIKE f.friendId.usernameFriend "
			+ "WHERE " 
				+ "f.friendId.usernameYou LIKE :username " 
				+ "AND f.isAccepted = false "
				+ "AND a.fullname LIKE %:keyword% "
			+ "ORDER BY f.requestTimeAt DESC ")
	List<Account> searchMakedFriendSearchs(@Param("username") String username, @Param("keyword") String keyword);
	
	@Transactional
    @Modifying
	@Query("DELETE FROM Friend f WHERE (f.friendId.usernameYou = :usernameYou AND f.friendId.usernameFriend = :usernameFriend) " +
	           "OR (f.friendId.usernameYou = :usernameFriend AND f.friendId.usernameFriend = :usernameYou)")
	int unfriend(String usernameYou, String usernameFriend);

}
