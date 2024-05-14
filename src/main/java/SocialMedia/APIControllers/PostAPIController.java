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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Auth.Registration.RegisterRequest;
import SocialMedia.Entities.Account;
import SocialMedia.Entities.Mode;
import SocialMedia.Entities.Post;
import SocialMedia.Entities.SocialGroup;
import SocialMedia.Models.PostModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IModeService;
import SocialMedia.Services.IPostService;
import SocialMedia.Services.ISocialGroupService;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/post")
public class PostAPIController {

	@Autowired
	IPostService postService;
	
	@Autowired
	IAccountService accountService;
	
	@Autowired
	IModeService modeService;
	
	@Autowired
	ISocialGroupService socialGroupService;
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getPostWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request) {
		
		List<Post> posts = postService.findAllPosts(username);
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
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", postModels), 
				HttpStatus.OK
		);
	}
	
    @PostMapping("/create")
    public ResponseEntity<?> register(@RequestBody PostModel postModel) {
    	if (postModel.getPostText() == "" && postModel.getPostMedia() == "")
    	{
            return new ResponseEntity<Response>(
    				new Response(false, "Create post false", null), 
    				HttpStatus.BAD_REQUEST
    		);
    	}
    	else 
    	{
    		Post post = new Post();
        	post.setText(postModel.getPostText());
        	post.setMediaURL(postModel.getPostMedia());
        	
        	//post.setPostTimeAt(LocalDateTime.parse(postModel.getPostingTimeAt()));
        	post.setPostTimeAt(LocalDateTime.now());
        	post.setDeleted(false);
        	
        	Optional<Account> optionalAccount = accountService.findByUsername(postModel.getUsername());
        	Account posterAccount = optionalAccount.orElse(null); 
        	post.setPosterAccount(posterAccount);
        	
        	Optional<Mode> optionalMode = modeService.findByModeId(postModel.getMode());
        	Mode mode = optionalMode.orElse(null);
        	post.setMode(mode);
        	
        	// Bai viet trong group
        	if (postModel.getMode() == 4)
        	{
            	Optional<SocialGroup> optionalSocialGroup = socialGroupService.findByGroupId(postModel.getGroupId());
            	SocialGroup socialGroup = optionalSocialGroup.orElse(null);
        		post.setGroup(socialGroup);
        	}
        	postService.save(post);
            return new ResponseEntity<Response>(
    				new Response(true, "Thành công", postModel), 
    				HttpStatus.OK
    		);
    	}
    	
    }
    
}
