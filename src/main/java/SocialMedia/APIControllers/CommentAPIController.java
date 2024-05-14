package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Comment;
import SocialMedia.Models.CommentModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.ICommentService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentAPIController {
	@Autowired
	ICommentService commentService;
	
	@GetMapping("/{postId}")
	public ResponseEntity<Response> getConversationsWithUsername(
			@PathVariable(value = "postId") Long postId, 
			HttpServletRequest request, 
			Model model){
		
		List<Comment> comments = commentService.findCommentsByPostId(postId);
		List<CommentModel> CommentModels = new ArrayList<>();
		
		for (Comment comment : comments) {
			CommentModel commentModel = new CommentModel();
			commentModel.setAvatar(comment.getCommenterAccount().getAvatarURL());
			commentModel.setUsername(comment.getCommenterAccount().getUsername());
			commentModel.setFullName(comment.getCommenterAccount().getFullname());
			commentModel.setCommentId(comment.getCommentId());
			commentModel.setCommentText(comment.getText());
			commentModel.setMediaURL(comment.getMediaURL());
			commentModel.setIsDeleted(comment.getIsDeleted());
			commentModel.setCommentTimeAt(comment.getCommentTimeAt().toString());
			commentModel.setMediaURL(comment.getMediaURL());
			
			CommentModels.add(commentModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", CommentModels), 
				HttpStatus.OK
		);
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
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", null),
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
				commentOld.setCommentTimeAt(java.time.LocalDateTime.now());
				commentService.save(commentOld);
			}
			return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", null),
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
