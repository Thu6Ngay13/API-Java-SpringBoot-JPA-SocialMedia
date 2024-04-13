package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Post;

public interface IPostService {
	List<Post> findAllPosts(String username);
	
}
