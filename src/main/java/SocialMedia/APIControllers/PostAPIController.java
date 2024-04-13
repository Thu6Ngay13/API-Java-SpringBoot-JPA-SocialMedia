package SocialMedia.APIControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Response.Response;
import SocialMedia.Services.IPostService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class PostAPIController {
	@Autowired
	IPostService postService;
	
	@GetMapping("/post/{username}")
	public ResponseEntity<?> Home(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request, 
			Model model) {
		
		return new ResponseEntity<Response>(
				new Response(true, "Tìm Thành công", postService.findAllPosts(username)), 
				HttpStatus.OK
		);
	}
}
