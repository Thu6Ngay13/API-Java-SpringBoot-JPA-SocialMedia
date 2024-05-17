package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
				postModel.setAvatar(post.getPosterAccount().getAvatarURL());
				postModel.setUsername(post.getPosterAccount().getUsername());
				postModel.setFullName(post.getPosterAccount().getFullname());
				postModel.setPostingTimeAt(post.getPostTimeAt().toString());
				postModel.setMode(post.getMode().getModeId());
				postModel.setPostText(post.getText());
				postModel.setPostMedia(post.getMediaURL());
				postModel.setLiked(false);
				postModels.add(postModel);
			}
			modelAcc.setPosts(postModels);
		} else {
			modelAcc = null;
		}
		return new ResponseEntity<Response>(new Response(true, "My account", modelAcc), HttpStatus.OK);
	}
}
