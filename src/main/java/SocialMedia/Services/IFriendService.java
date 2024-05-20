package SocialMedia.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;

public interface IFriendService {
	List<Account> findAllYourFriends(String username);
	List<Object[]> findAllFriendRequests(String username);
	Optional<Account> findFriendRequestWith2To1(String username1, String username2);
	Optional<Account> checkIsFriend(String username1, String username2);
	void acceptFriend(String username1, String username2);
	void declineFriend(String username1, String username2);
	List<Account> searchAllYourFriends(String username, String keyword);
	List<Object[]> searchAllFriendRequests(String username, String keyword);
	List<Account> searchNotFriend(String username, String keyword);
	List<Account> searchMakedFriendSearchs(String username, String keyword);
	void makeFriend(String username1, String username2);
	Set<Account> getSearchFriend(String username);
	int unfriend(String usernameYou, String usernameFriend);
	Optional<Friend> findById(FriendId friendId);
}
