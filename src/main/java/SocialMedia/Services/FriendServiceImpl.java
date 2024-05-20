package SocialMedia.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.FriendRepository;

@Service
public class FriendServiceImpl implements IFriendService{
	
	@Autowired
	FriendRepository friendRepository;

	@Autowired
	AccountRepository accountRepository;
		
	@Override
	public List<Account> findAllYourFriends(String username) {
		return friendRepository.findAllYourFriends(username);
	}

	@Override
	public List<Object[]> findAllFriendRequests(String username) {
		return friendRepository.findAllFriendRequests(username);
	}
	
	@Override
	public Optional<Account> findFriendRequestWith2To1(String username1, String username2){
		return friendRepository.findFriendRequestWith2To1(username1, username2);
	}
	
	@Override
	public Optional<Account> checkIsFriend(String username1, String username2) {
		return friendRepository.checkIsFriend(username1, username2);
	}

	@Override
	public void acceptFriend(String username1, String username2) {
		Optional<Friend> friendRequest = friendRepository.findById(new FriendId(username2, username1));
		if(friendRequest.isPresent()) {
			Friend friend = friendRequest.get();
			friend.setAccepted(true);
			friendRepository.save(friend);
		}
	}

	@Override
	public void declineFriend(String username1, String username2) {
		Optional<Friend> friendRequest = friendRepository.findById(new FriendId(username2, username1));
		if(friendRequest.isPresent()) {
			Friend friend = friendRequest.get();
			friendRepository.delete(friend);
		}
	}

	@Override
	public List<Account> searchAllYourFriends(String username, String keyword) {
		return friendRepository.searchAllYourFriends(username, keyword);
	}

	@Override
	public List<Object[]> searchAllFriendRequests(String username, String keyword) {
		return friendRepository.searchAllFriendRequests(username, keyword);
	}

	@Override
	public List<Account> searchNotFriend(String username, String keyword) {
		return friendRepository.searchNotFriend(username, keyword);
	}
	
	@Override
	public List<Account> searchMakedFriendSearchs(String username, String keyword) {
		return friendRepository.searchMakedFriendSearchs(username, keyword);
	}

	@Override
	public void makeFriend(String username1, String username2) {
		Optional<Account> acocount1 = accountRepository.findByUsername(username1);
		Optional<Account> acocount2 = accountRepository.findByUsername(username2);
		
		if (acocount1.isPresent() && acocount2.isPresent()) {
			
			FriendId friendId = new FriendId(username1, username2);
			Friend friend = new Friend(friendId, LocalDateTime.now(), false);
			friendRepository.save(friend);
		}
	}

	@Override
	public Set<Account> getSearchFriend(String username) {
		Optional<Account> accountYou = accountRepository.findByUsername(username);
		Set<Account> accountSuggests = new HashSet<>();		
		
		if (accountYou.isPresent()) {
			List<Account> accountYourFriends = findAllYourFriends(username);
			List<Account> accountFriendRequests = new ArrayList<>();
			
			List<Object[]> accountFriendRequestAndTimeRequests = findAllFriendRequests(username);
			for (Object[] objects : accountFriendRequestAndTimeRequests) {
				Account account = (Account) objects[0];
				accountFriendRequests.add(account);
			}

			List<Account> accountTemps = new ArrayList<>();
			accountTemps.add(accountYou.get());
			accountTemps.addAll(accountYourFriends);
			accountTemps.addAll(accountFriendRequests);
			
			for (Account account : accountYourFriends) {
				List<Account> accountFriendOfFriends = findAllYourFriends(account.getUsername());			
				accountSuggests.addAll(accountFriendOfFriends);
			}
			
			accountSuggests.removeAll(accountTemps);
		}
		
		return accountSuggests;
	}

	@Override
	public int unfriend(String usernameYou, String usernameFriend) {
		return friendRepository.unfriend(usernameYou, usernameFriend);
	}

	@Override
	public Optional<Friend> findById(FriendId friendId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
