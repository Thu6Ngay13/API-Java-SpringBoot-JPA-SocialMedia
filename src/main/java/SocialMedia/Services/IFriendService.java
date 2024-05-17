package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import SocialMedia.Entities.Account;

public interface IFriendService {
	List<Account> findAllYourFriends(String username);
	List<Object[]> findAllFriendRequests(String username);
	Optional<Account> findFriendRequestWith2To1(String username1, String username2);
	Optional<Account> checkIsFriend(String username1, String username2);
	void acceptFriend(String username1, String username2);
	void declineFriend(String username1, String username2);
}
