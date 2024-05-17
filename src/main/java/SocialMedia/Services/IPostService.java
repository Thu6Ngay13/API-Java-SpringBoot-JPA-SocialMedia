package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import SocialMedia.Entities.Post;

public interface IPostService {
	List<Post> findPostOfNewFeedWithUsername(String username, Pageable pageable);
	Optional<Post> findPostWithUsernameAndPostId(@Param("username") String username, @Param("postId") long postId);
	void likePost(Post post, String username);
	void unlikePost(Post post, String username);
	void sharePost(Post post, String username);
	
	Optional<Post> findById(Long postId);

	void deleteById(Long id);
	
	<S extends Post> S save(S entity);
	
	void share(String username, long postId);
	
	int existAccountsharePost(String username, long postId);
}
