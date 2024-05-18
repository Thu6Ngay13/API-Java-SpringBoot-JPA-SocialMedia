package SocialMedia.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Like;
import SocialMedia.Entities.LikeId;
import SocialMedia.Entities.Post;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.LikeRepository;
import SocialMedia.Repositories.PostRepository;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	LikeRepository likeRepository;

	@Override
	public List<Post> findPostOfNewFeedWithUsername(String username, Pageable pageable) {
		return postRepository.findPostOfNewFeedWithUsername(username, pageable);
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
	public Optional<Post> findPostWithUsernameAndPostId(String username, long postId) {
		return postRepository.findPostWithUsernameAndPostId(username, postId);
	}

	@Override
	public void likePost(Post post, String username) {
		Set<Account> likersAccounts = post.getAccountLikes();
		Optional<Account> likerAccount = accountRepository.findByUsername(username);

		if (likerAccount.isPresent()) {
			Account account = likerAccount.get();
			likersAccounts.add(account);
			post.setAccountLikes(likersAccounts);

			Set<Post> likedPosts = account.getLikedPosts();
			likedPosts.add(post);
			account.setLikedPosts(likedPosts);

			accountRepository.save(account);
		}

		postRepository.save(post);

		long postId = post.getPostId();
		Optional<Like> likeNew = likeRepository.findById(new LikeId(postId, username));
		if (likeNew.isPresent()) {
			Like like = likeNew.get();
			like.setLikeTimeAt(LocalDateTime.now());
			likeRepository.save(like);
		}
	}

	@Override
	public void unlikePost(Post post, String username) {
		Set<Account> likersAccounts = post.getAccountLikes();
		Optional<Account> likerAccount = accountRepository.findByUsername(username);

		if (likerAccount.isPresent()) {
			Account account = likerAccount.get();
			likersAccounts.remove(account);
			post.setAccountLikes(likersAccounts);

			Set<Post> likedPosts = account.getLikedPosts();
			likedPosts.remove(post);
			account.setLikedPosts(likedPosts);

			accountRepository.save(account);
		}

		postRepository.save(post);

		long postId = post.getPostId();
		Optional<Like> likeNew = likeRepository.findById(new LikeId(postId, username));
		if (likeNew.isPresent()) {
			Like like = likeNew.get();
			likeRepository.delete(like);
		}
	}

	@Override
	public void sharePost(Post post, String username) {
		Set<Account> sharerAccounts = post.getSharerAccounts();
		Optional<Account> sharerAccount = accountRepository.findByUsername(username);

		if (sharerAccount.isPresent()) {
			Account account = sharerAccount.get();
			sharerAccounts.add(account);
			post.setSharerAccounts(sharerAccounts);

			Set<Post> postShares = account.getPostShares();
			postShares.add(post);
			account.setPostShares(postShares);

			accountRepository.save(account);
		}

		postRepository.save(post);
	}
	
	@Override
	public List<Post> findAllPostByUsernameOrderByPostTimeAtDesc(String username) {
		return postRepository.findAllPostByUsernameOrderByPostTimeAtDesc(username);
	}
	
	@Override
	public List<Post> findPostsByGroupId(long groupId) {
		return postRepository.findPostsByGroupId(groupId);
	}

	@Override
	public List<Post> findAllPostOfFriendByUsernameOrderByPostTimeAtDesc(String username) {
		// TODO Auto-generated method stub
		return postRepository.findAllPostOfFriendByUsernameOrderByPostTimeAtDesc(username);
	}
	
	@Override
	public List<Post> findPostInGroupsByUsername(String username) {
		return postRepository.findPostInGroupsByUsername(username);
	}
}
