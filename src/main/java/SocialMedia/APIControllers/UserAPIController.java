package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;
import SocialMedia.Models.AccountModel;
import SocialMedia.Models.PostModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IPostService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

	@Autowired
	IAccountService accountService;

	@Autowired
	IPostService postService;

	@GetMapping("/my-account/{username}")
	public ResponseEntity<?> getAccountByUsername(@PathVariable(value = "username") String username,
			HttpServletRequest request) {
		Optional<Account> optAcc = accountService.findByUsername(username);
		Account myAccount = optAcc.orElse(null);
		AccountModel modelAcc = new AccountModel();
		List<AccountModel> listFriend = new ArrayList<>();
		if (myAccount != null) {
			modelAcc.setUsername(myAccount.getUsername());
			modelAcc.setFullname(myAccount.getFullname());
			modelAcc.setGender(myAccount.getGender());
			modelAcc.setAvatarURL(myAccount.getAvatarURL());
			modelAcc.setEmail(myAccount.getEmail());
			modelAcc.setPhoneNumber(myAccount.getPhoneNumber());
			modelAcc.setDescription(myAccount.getDescription());
			modelAcc.setCompany(myAccount.getCompany());
			modelAcc.setLocation(myAccount.getLocation());
			modelAcc.setSingle(myAccount.isSingle());
			modelAcc.setRole(myAccount.getRole());
			modelAcc.setCountFriend(accountService.countFriend(username));
			for (String u : accountService.getAcceptedFriends(username)) {
				if (accountService.findByUsername(u).get() != null) {
					Account a = accountService.findByUsername(u).get();
					AccountModel modelFriend = new AccountModel();
					modelFriend.setUsername(a.getUsername());
					modelFriend.setFullname(a.getFullname());
					modelFriend.setGender(a.getGender());
					modelFriend.setAvatarURL(a.getAvatarURL());
					modelFriend.setEmail(a.getEmail());
					modelFriend.setPhoneNumber(a.getPhoneNumber());
					modelFriend.setDescription(a.getDescription());
					modelFriend.setCompany(a.getCompany());
					modelFriend.setLocation(a.getLocation());
					modelFriend.setSingle(a.isSingle());
					modelFriend.setRole(a.getRole());
					listFriend.add(modelFriend);
				}
			}
			modelAcc.setFriends(listFriend);
			List<Post> posts = postService.findAllPostByUsernameOrderByPostTimeAtDesc(username);
			List<PostModel> postModels = new ArrayList<>();

			for (Post post : posts) {
				PostModel postModel = new PostModel();
				postModel.setPostId(post.getPostId());
				postModel.setAvatar(post.getPosterAccount().getAvatarURL());
				postModel.setUsername(post.getPosterAccount().getUsername());
				postModel.setFullName(post.getPosterAccount().getFullname());
				postModel.setPostingTimeAt(post.getPostTimeAt().toString());
				postModel.setMode(post.getMode().getModeId());
				postModel.setPostText(post.getText());
				postModel.setPostMedia(post.getMediaURL());
				boolean liked = false;
				for (Account account : post.getAccountLikes()) {
					if (account.getUsername().equals(username)) {
						liked = true;
						break;
					}
				}

				postModel.setLiked(liked);
				postModels.add(postModel);
			}
			modelAcc.setPosts(postModels);
		} else {
			modelAcc = null;
		}
		return new ResponseEntity<Response>(new Response(true, "My account", modelAcc), HttpStatus.OK);
	}
	@GetMapping("/my-account/{username}/friend-account/{usernameFriend}")
	public ResponseEntity<?> getFriendAccountByUsername(@PathVariable(value = "username") String username,
			@PathVariable(value = "usernameFriend") String usernameFriend,
			HttpServletRequest request) {
		Optional<Account> optAcc = accountService.findByUsername(usernameFriend);
		Account friendAccount = optAcc.orElse(null);
		AccountModel modelAcc = new AccountModel();
		List<AccountModel> listFriend = new ArrayList<>();
		if (friendAccount != null) {
			modelAcc.setUsername(friendAccount.getUsername());
			modelAcc.setFullname(friendAccount.getFullname());
			modelAcc.setGender(friendAccount.getGender());
			modelAcc.setAvatarURL(friendAccount.getAvatarURL());
			modelAcc.setEmail(friendAccount.getEmail());
			modelAcc.setPhoneNumber(friendAccount.getPhoneNumber());
			modelAcc.setDescription(friendAccount.getDescription());
			modelAcc.setCompany(friendAccount.getCompany());
			modelAcc.setLocation(friendAccount.getLocation());
			modelAcc.setSingle(friendAccount.isSingle());
			modelAcc.setRole(friendAccount.getRole());
			modelAcc.setCountFriend(accountService.countFriend(usernameFriend));
			for (String u : accountService.getAcceptedFriends(usernameFriend)) {
				if (accountService.findByUsername(u).get() != null) {
					Account a = accountService.findByUsername(u).get();
					AccountModel modelFriend = new AccountModel();
					modelFriend.setUsername(a.getUsername());
					modelFriend.setFullname(a.getFullname());
					modelFriend.setGender(a.getGender());
					modelFriend.setAvatarURL(a.getAvatarURL());
					modelFriend.setEmail(a.getEmail());
					modelFriend.setPhoneNumber(a.getPhoneNumber());
					modelFriend.setDescription(a.getDescription());
					modelFriend.setCompany(a.getCompany());
					modelFriend.setLocation(a.getLocation());
					modelFriend.setSingle(a.isSingle());
					modelFriend.setRole(a.getRole());
					listFriend.add(modelFriend);
				}
			}
			modelAcc.setFriends(listFriend);
			List<Post> posts = postService.findAllPostOfFriendByUsernameOrderByPostTimeAtDesc(usernameFriend);
			List<PostModel> postModels = new ArrayList<>();

			for (Post post : posts) {
				PostModel postModel = new PostModel();
				postModel.setPostId(post.getPostId());
				postModel.setAvatar(post.getPosterAccount().getAvatarURL());
				postModel.setUsername(post.getPosterAccount().getUsername());
				postModel.setFullName(post.getPosterAccount().getFullname());
				postModel.setPostingTimeAt(post.getPostTimeAt().toString());
				postModel.setMode(post.getMode().getModeId());
				postModel.setPostText(post.getText());
				postModel.setPostMedia(post.getMediaURL());
				boolean liked = false;
				for (Account account : post.getAccountLikes()) {
					if (account.getUsername().equals(username)) {
						liked = true;
						break;
					}
				}

				postModel.setLiked(liked);
				if (accountService.getAcceptedFriends(usernameFriend).contains(username)) {
					postModels.add(postModel);
				}
				else if (postModel.getMode() == 1) {
					postModels.add(postModel);
				}
			}
			modelAcc.setPosts(postModels);
		} else {
			modelAcc = null;
		}
		return new ResponseEntity<Response>(new Response(true, "My friend account", modelAcc), HttpStatus.OK);
	}
	@PutMapping("/my-account/update")
    public ResponseEntity<Response> updateProfile(@RequestParam String fullname, @RequestParam String gender, 
                                                @RequestParam String description, @RequestParam String company, 
                                                @RequestParam String location, @RequestParam boolean isSingle, 
                                                @RequestParam String username) {
        int result = accountService.updateProfile(fullname, gender, description, company, location, isSingle, username);
        if (result > 0) {
            return new ResponseEntity<Response>(new Response(true, "Update profile successfully", null), HttpStatus.OK);
        } else {
        	return new ResponseEntity<Response>(new Response(false, "Update fail", null), HttpStatus.OK);
        }
    }
	@PutMapping("/my-account/{username}/updateAvatar")
    public ResponseEntity<Response> updateAvatar(@RequestParam String username, @RequestBody Map<String, String> reqBody) {
		String avatarURL = reqBody.get("avatarURL");
        int result = accountService.updateAvatar(username, avatarURL);
        if (result > 0) {
            return new ResponseEntity<Response>(new Response(true, "Update avatar successfully", null), HttpStatus.OK);
        } else {
        	return new ResponseEntity<Response>(new Response(false, "Update fail", null), HttpStatus.OK);
        }
    }
}
