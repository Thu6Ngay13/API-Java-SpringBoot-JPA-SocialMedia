package SocialMedia.Services;

import java.util.List;

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
}
