package SocialMedia.APIControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Comment;
import SocialMedia.Entities.Post;
import SocialMedia.Models.CommentModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.ICommentService;
import SocialMedia.Services.IModeService;
import SocialMedia.Services.IPostService;
import SocialMedia.Services.ISocialGroupService;
import SocialMedia.Services.IStoreFilesToDriver;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentAPIController {
	@Autowired
	ICommentService commentService;
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
	
	@GetMapping("/{postId}")
	public ResponseEntity<Response> getCommentByPostId(
			@PathVariable(value = "postId") Long postId)
	{
		List<Comment> comments = commentService.findCommentsByPostId(postId);
		List<CommentModel> CommentModels = new ArrayList<>();
		
		for (Comment comment : comments) {
			CommentModel commentModel = new CommentModel();
			commentModel.setAvatar(comment.getCommenterAccount().getAvatarURL());
			commentModel.setUsername(comment.getCommenterAccount().getUsername());
			commentModel.setFullName(comment.getCommenterAccount().getFullname());
			commentModel.setCommentId(comment.getCommentId());
			commentModel.setCommentText(comment.getText());
			commentModel.setCommentImage(comment.getMediaURL());
			commentModel.setIsDeleted(comment.getIsDeleted());
			commentModel.setCommentTimeAt(comment.getCommentTimeAt().toString());
			commentModel.setCommentImage(comment.getMediaURL());
			commentModel.setPostId(comment.getPost().getPostId());
			CommentModels.add(commentModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", CommentModels), 
				HttpStatus.OK
		);
	}
    
	@PostMapping("/create")
	public ResponseEntity<?> createComment(@RequestBody CommentModel commentModel) {
		if (commentModel.getCommentText() == "" && commentModel.getCommentImage() == "") {
			return new ResponseEntity<Response>(new Response(false, "Create comment false", null), HttpStatus.BAD_REQUEST);
		} else {
			Comment comment = new Comment();
			comment.setText(commentModel.getCommentText());
			comment.setMediaURL(commentModel.getCommentImage());

			// post.setPostTimeAt(LocalDateTime.parse(postModel.getPostingTimeAt()));
			comment.setCommentTimeAt(LocalDateTime.now());
			comment.setIsDeleted(false);

			Optional<Account> optionalAccount = accountService.findByUsername(commentModel.getUsername());
			Account account = optionalAccount.orElse(null);
			comment.setCommenterAccount(account);
			
			Optional<Post> optionalPost = postService.findById(commentModel.getPostId());
			Post post = optionalPost.orElse(null);
			comment.setPost(post);
			
			commentService.save(comment);

			commentModel.setCommentId(comment.getCommentId());
			commentModel.setPostId(post.getPostId());
			commentModel.setCommentTimeAt((comment.getCommentTimeAt()).toString());
			return new ResponseEntity<Response>(new Response(true, "Thành công", commentModel), HttpStatus.OK);
		}
	}
    
	@PutMapping(path = "/updateComment")
	public ResponseEntity<?> updateComment(@RequestBody CommentModel commentModel)
	{
		Comment commentOld = commentService.getById(commentModel.getCommentId());
		if (commentOld == null) {
			return new ResponseEntity<Response>(new Response(false, "Không tìm thấy comment", null),
					HttpStatus.BAD_REQUEST);
		} else {
			if (commentModel.getCommentText() != commentOld.getText()) {
				commentOld.setText(commentModel.getCommentText());
				commentOld.setCommentTimeAt(java.time.LocalDateTime.now());
				commentService.save(commentOld);
			}
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", commentModel),
					HttpStatus.OK);
		}
	}
	
	@PutMapping(path = "/update")
	public ResponseEntity<?> updateComment(@Validated @RequestParam("commentId") Long commentId,
			@Validated @RequestParam("commentText") String commentText)
	{
		Comment commentOld = commentService.getById(commentId);
		if (commentOld == null) {
			return new ResponseEntity<Response>(new Response(false, "Không tìm thấy comment", null),
					HttpStatus.BAD_REQUEST);
		} else {
			if (commentText != commentOld.getText()) {
				commentOld.setText(commentText);
				commentService.save(commentOld);
			}
			CommentModel commentModel = new CommentModel();
			commentModel.setAvatar(commentOld.getCommenterAccount().getAvatarURL());
			commentModel.setUsername(commentOld.getCommenterAccount().getUsername());
			commentModel.setFullName(commentOld.getCommenterAccount().getFullname());
			commentModel.setCommentId(commentOld.getCommentId());
			commentModel.setCommentText(commentOld.getText());
			commentModel.setCommentImage(commentOld.getMediaURL());
			commentModel.setIsDeleted(commentOld.getIsDeleted());
			commentModel.setCommentTimeAt(commentOld.getCommentTimeAt().toString());
			commentModel.setCommentImage(commentOld.getMediaURL());
			commentModel.setPostId(commentOld.getPost().getPostId());
			
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", commentModel),
					HttpStatus.OK);
		}
	}
	
	@PutMapping(path = "/delete/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId, 
			HttpServletRequest request, 
			Model model)
	{
		Comment comment = commentService.getById(commentId);
		if (comment == null) {
			return new ResponseEntity<Response>(new Response(false, "Không tìm thấy comment", null),
					HttpStatus.BAD_REQUEST);
		} else {
			comment.setIsDeleted(true);
			commentService.save(comment);
			return new ResponseEntity<Response>(new Response(true, "Xóa comment Thành công", null),
					HttpStatus.OK);
		}
	}
}
