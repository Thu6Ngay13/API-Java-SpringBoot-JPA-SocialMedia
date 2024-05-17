package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;
import SocialMedia.Repositories.FriendRepository;

@Service
public class FriendServiceImpl implements IFriendService{
	
	@Autowired
	FriendRepository friendRepository;

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
		Optional<Friend> friendRequest = friendRepository.findById(new FriendId(username1, username2));
		
		if(friendRequest.isPresent()) {
			Friend friend = friendRequest.get();
			friend.setIsAccepted(true);
			friendRepository.save(friend);
		}
	}

	@Override
	public void declineFriend(String username1, String username2) {
		Optional<Friend> friendRequest = friendRepository.findById(new FriendId(username1, username2));
		if(friendRequest.isPresent()) {
			Friend friend = friendRequest.get();
			friendRepository.delete(friend);
		}
	}

}
