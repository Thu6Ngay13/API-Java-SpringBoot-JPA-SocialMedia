package SocialMedia.APIControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;
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
	public ResponseEntity<?> getYourFriendsWithUsername(@PathVariable(value = "username") String username,
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

		return new ResponseEntity<Response>(new Response(true, "Thành công", friendModels), HttpStatus.OK);
	}

	@GetMapping("/friendrequest/{username}")
	public ResponseEntity<?> getFriendRequestsWithUsername(@PathVariable(value = "username") String username,
			HttpServletRequest request, Model model) {

		List<Object[]> friends = friendService.findAllFriendRequests(username);
		List<FriendModel> friendModels = new ArrayList<>();

		for (Object[] friend : friends) {
			Account account = (Account) friend[0];
			LocalDateTime requestTimeAt = (LocalDateTime) friend[1];

			FriendModel friendModel = new FriendModel();
			friendModel.setViewType(TypeFriendEnum.FRIEND_REQUEST);
			friendModel.setAvatar(account.getAvatarURL());
			friendModel.setFullName(account.getFullname());
			friendModel.setUsername(account.getUsername());
			friendModel.setRequestTimeAt(requestTimeAt.toString());
			friendModels.add(friendModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", friendModels), HttpStatus.OK);
	}

	@PostMapping("/friendrequest/{username1}/accept/{username2}")
	public ResponseEntity<?> acceptFriend(@PathVariable("username1") String username1,
			@PathVariable("username2") String username2) {

		Optional<Account> account = friendService.findFriendRequestWith2To1(username1, username2);
		if (account.isPresent()) {
			friendService.acceptFriend(username1, username2);
			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/friendrequest/{username1}/decline/{username2}")
	public ResponseEntity<?> declineFriend(@PathVariable("username1") String username1,
			@PathVariable("username2") String username2) {

		Optional<Account> account = friendService.findFriendRequestWith2To1(username1, username2);
		if (account.isPresent()) {
			friendService.declineFriend(username1, username2);
			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/{username1}/make/{username2}")
	public ResponseEntity<?> makeFriend(@PathVariable("username1") String username1,
			@PathVariable("username2") String username2) {

		Optional<Friend> friendCheck = friendService.findById(new FriendId(username2, username1));

		if (friendCheck.isEmpty()) {
			friendService.makeFriend(username1, username2);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

	@PostMapping("/{username1}/unmake/{username2}")
	public ResponseEntity<?> unmakeFriend(@PathVariable("username1") String username1,
			@PathVariable("username2") String username2) {

		Optional<Account> account = friendService.findFriendRequestWith2To1(username2, username1);
		if (account.isPresent()) {
			friendService.declineFriend(username2, username1);
			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}
	@PostMapping("/{usernameYou}/unfriend/{usernameFriend}")
	public ResponseEntity<?> unfriend(@PathVariable("usernameYou") String usernameYou,
			@PathVariable("usernameFriend") String usernameFriend) {
		int result = friendService.unfriend(usernameYou, usernameFriend);
		if (result > 0) {
			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}
		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}
}
