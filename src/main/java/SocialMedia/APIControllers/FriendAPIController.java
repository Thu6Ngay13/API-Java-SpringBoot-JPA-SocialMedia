package SocialMedia.APIControllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Enums.TypeFriendEnum;
import SocialMedia.Models.FriendModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IFriendService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/friend")
public class FriendAPIController {
	@Autowired
	IFriendService friendService;

	@GetMapping("/yourfriend/{username}")
	public ResponseEntity<?> getYourFriendsWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request) {
		
		List<Account> friends = friendService.findAllYourFriends(username);
		List<FriendModel> friendModels = new ArrayList<>();
		
		for (Account friend : friends) {
			FriendModel friendModel = new FriendModel();
			friendModel.setViewType(TypeFriendEnum.YOUR_FRIEND);
			friendModel.setAvatar(friend.getAvatarURL());
			friendModel.setFullName(friend.getFullname());
			friendModel.setUsername(friend.getUsername());
			friendModels.add(friendModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", friendModels), 
				HttpStatus.OK
		);
	}
	
	@GetMapping("/friend/friendrequest/{username}")
	public ResponseEntity<?> getFriendRequestsWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request, 
			Model model) {
		
		List<Account> friends = friendService.findAllFriendRequests(username);
		List<FriendModel> friendModels = new ArrayList<>();
		
		for (Account friend : friends) {
			FriendModel friendModel = new FriendModel();
			friendModel.setViewType(TypeFriendEnum.FRIEND_REQUEST);
			friendModel.setAvatar(friend.getAvatarURL());
			friendModel.setFullName(friend.getFullname());
			friendModel.setUsername(friend.getUsername());
			friendModel.setRequestTimeAt(LocalDate.now().toString());
			friendModels.add(friendModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", friendModels), 
				HttpStatus.OK
		);
	}
}
