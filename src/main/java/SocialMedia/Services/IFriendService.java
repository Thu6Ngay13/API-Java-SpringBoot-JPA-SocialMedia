package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Account;

public interface IFriendService {
	List<Account> findAllYourFriends(String username);
	List<Account> findAllFriendRequests(String username);
}
