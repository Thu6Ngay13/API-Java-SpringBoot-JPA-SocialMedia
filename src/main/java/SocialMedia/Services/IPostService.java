package SocialMedia.Services;

import java.util.List;
import java.util.Optional;
import SocialMedia.Entities.Post;

public interface IPostService {
	List<Post> findAllPosts(String username);
	
	Optional<Post> findById(Long postId);

	void deleteById(Long id);
	
	<S extends Post> S save(S entity);
	
}
