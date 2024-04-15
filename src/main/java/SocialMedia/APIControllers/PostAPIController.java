package SocialMedia.APIControllers;

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

import SocialMedia.Entities.Post;
import SocialMedia.Models.PostModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IPostService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class PostAPIController {
	@Autowired
	IPostService postService;
	
	@GetMapping("/post/{username}")
	public ResponseEntity<?> getPostWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request, 
			Model model) {
		
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
}
