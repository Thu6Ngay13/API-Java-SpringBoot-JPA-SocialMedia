package SocialMedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
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
	public List<Account> findAllFriendRequests(String username) {
		return friendRepository.findAllFriendRequests(username);
	}

}
