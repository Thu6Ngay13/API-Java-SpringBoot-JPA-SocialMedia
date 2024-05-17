package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SocialMedia.Entities.Post;
import SocialMedia.Repositories.PostRepository;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	PostRepository postRepository;

	@Override
	public List<Post> findAllPosts(String username) {
		return postRepository.findAllPosts(username);
	}
	
	@Override
	public List<Post> findPostsByGroupId(long groupId) {
		return postRepository.findPostsByGroupId(groupId);
	}
	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}
	
	@Override
	public <S extends Post> S save(S entity) {
		return postRepository.save(entity);
	}

	@Override
	public void share(String username, long postId) {
		postRepository.share(username, postId);
	}

	@Override
	public int existAccountsharePost(String username, long postId) {
		return postRepository.existAccountsharePost(username, postId);
	}

	@Override
	public List<Post> findAllPostByUsernameOrderByPostTimeAtDesc(String username) {
		return postRepository.findAllPostByUsernameOrderByPostTimeAtDesc(username);
	}
	
	
}
