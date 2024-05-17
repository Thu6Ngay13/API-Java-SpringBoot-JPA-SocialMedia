package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Comment;

public interface ICommentService {
	List<Comment> findCommentsByPostId(Long postId);
	void deleteById(Long id);
	Comment getById(Long id);
	<S extends Comment> S save(S entity);
}
