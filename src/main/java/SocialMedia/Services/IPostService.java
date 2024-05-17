package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import SocialMedia.Entities.Post;

public interface IPostService {
	List<Post> findAllPosts(String username);
	
	Optional<Post> findById(Long postId);

	void deleteById(Long id);
	
	<S extends Post> S save(S entity);
	
	void share(String username, long postId);
	
	int existAccountsharePost(String username, long postId);
	
	List<Post> findAllPostByUsernameOrderByPostTimeAtDesc(String username);
	
	List<Post> findPostsByGroupId(long groupId);
}
