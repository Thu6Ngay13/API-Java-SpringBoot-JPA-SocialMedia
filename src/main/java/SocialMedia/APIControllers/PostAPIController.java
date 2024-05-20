package SocialMedia.APIControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Mode;
import SocialMedia.Entities.Post;
import SocialMedia.Models.PostModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IModeService;
import SocialMedia.Services.IPostService;
import SocialMedia.Services.ISocialGroupService;
import SocialMedia.Services.IStoreFilesToDriver;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/post")
public class PostAPIController {

	@Autowired
	IStoreFilesToDriver storeFiles;

	@Autowired
	IPostService postService;

	@Autowired
	IAccountService accountService;

	@Autowired
	IModeService modeService;

	@Autowired
	ISocialGroupService socialGroupService;

	@GetMapping("/{username}/{page}/{pageSize}")
	public ResponseEntity<?> getPostOfNewFeedWithUsername(@PathVariable(value = "username") String username,
			@PathVariable(value = "page") int page, @PathVariable(value = "pageSize") int pageSize,
			HttpServletRequest request) {

		Pageable pageable = PageRequest.of(page, pageSize);
		List<Post> posts = postService.findPostOfNewFeedWithUsername(username, pageable);
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

		Collections.shuffle(postModels);
		return new ResponseEntity<Response>(new Response(true, "Thành công", postModels), HttpStatus.OK);
	}

	@PostMapping("/{username}/like/{postId}")
	public ResponseEntity<?> likePost(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, HttpServletRequest request) {

		Optional<Post> post = postService.findPostWithUsernameAndPostId(username, postId);
		if (post.isPresent()) {
			postService.likePost(post.get(), username);

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/{username}/unlike/{postId}")
	public ResponseEntity<?> unlikePost(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, HttpServletRequest request) {

		Optional<Post> post = postService.findPostWithUsernameAndPostId(username, postId);
		if (post.isPresent()) {
			postService.unlikePost(post.get(), username);

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/{username}/share/{postId}")
	public ResponseEntity<?> sharePost(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, HttpServletRequest request) {

		Optional<Post> post = postService.findPostWithUsernameAndPostId(username, postId);
		if (post.isPresent()) {
			postService.sharePost(post.get(), username);

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createPost(@RequestBody PostModel postModel) {
		if (postModel.getPostText() == "" && postModel.getPostMedia() == "") {
			return new ResponseEntity<Response>(new Response(false, "Create post false", null), HttpStatus.BAD_REQUEST);
		} else {
			Post post = new Post();
			post.setText(postModel.getPostText());
			post.setMediaURL(postModel.getPostMedia());

			// post.setPostTimeAt(LocalDateTime.parse(postModel.getPostingTimeAt()));
			post.setPostTimeAt(LocalDateTime.now());
			post.setDeleted(false);

			Optional<Account> optionalAccount = accountService.findByUsername(postModel.getUsername());
			Account posterAccount = optionalAccount.orElse(null);
			post.setPosterAccount(posterAccount);

			Optional<Mode> optionalMode = modeService.findByModeId(postModel.getMode());
			Mode mode = optionalMode.orElse(null);
			post.setMode(mode);

			postService.save(post);

			postModel.setPostId(post.getPostId());
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			return new ResponseEntity<Response>(new Response(true, "Thành công", postModel), HttpStatus.OK);
		}
	}

	@PostMapping("/media")
	public ResponseEntity<Response> mediaPost(@RequestParam("media") MultipartFile media, HttpServletRequest request) {
		String mediaUrl = "";
		try {
			File file = new File("D:/Lap_trinh_di_dong/File-SocialMedia/" + media.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file);

			fos.write(media.getBytes());
			fos.close();
			mediaUrl = storeFiles.uploadImageToDrive(file);
			return new ResponseEntity<Response>(new Response(true, "Thành công", mediaUrl), HttpStatus.OK);

		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(new Response(false, "Thất bại", mediaUrl), HttpStatus.OK);

		}
	}

}
