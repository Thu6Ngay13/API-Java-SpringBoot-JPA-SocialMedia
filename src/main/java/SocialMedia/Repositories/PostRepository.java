package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Post;
import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query(value = "SELECT DISTINCT p " 
			+ "FROM Post p "
			+ "LEFT JOIN Friend f ON (p.posterAccount.username LIKE f.friendId.usernameYou "
								+ "OR p.posterAccount.username LIKE f.friendId.usernameFriend) "
			+ "JOIN Account a ON p.posterAccount.username LIKE a.username "
			+ "WHERE "
				+ "a.isBanned = false "
				+ "AND a.enable = true "
				+ "AND p.isDeleted = false "
				+ "AND (p.mode.modeId = 1) OR "
				+ "((f.id.usernameYou LIKE :username OR f.id.usernameFriend LIKE :username) "
					+ "AND p.mode.modeId = 2 "
					+ "AND f.isAccepted = true) "
				//+ "AND p.group.groupId = -1 "
				+ "")
	List<Post> findPostOfNewFeedWithUsername(@Param("username") String username, Pageable pageable);

	Optional<Post> findByPostId(Integer id);

	@Query(value = "SELECT DISTINCT p " 
			+ "FROM Post p "
			+ "LEFT JOIN Friend f ON (p.posterAccount.username LIKE f.friendId.usernameYou "
								+ "OR p.posterAccount.username LIKE f.friendId.usernameFriend) "
			+ "JOIN Account a ON p.posterAccount.username LIKE a.username "
			+ "WHERE "
				+ "a.isBanned = false "
				+ "AND a.enable = true "
				+ "AND p.isDeleted = false "
				+ "AND (p.mode.modeId = 1) OR "
				+ "((f.id.usernameYou LIKE :username OR f.id.usernameFriend LIKE :username) "
					+ "AND p.mode.modeId = 2 "
					+ "AND f.isAccepted = true) "
				+ "AND p.postId = :postId "
				//+ "AND p.group.groupId = -1 "
				+ "")
	Optional<Post> findPostWithUsernameAndPostId(@Param("username") String username, @Param("postId") long postId);

	@Query(value = "SELECT COUNT(*) FROM Comment WHERE postId = :postId", nativeQuery = true)
	int countCommentsByPostId(@Param("postId") long postId);

	@Override
	default Post getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Account_share_Post (username, post_id) VALUES (:username, :postId)", nativeQuery = true)
	void share(@Param("username") String username, @Param("postId") long postId);

	@Query(value = "SELECT COUNT(*) FROM Account_share_Post WHERE username = :username and post_id = :postId", nativeQuery = true)
	int existAccountsharePost(@Param("username") String username, @Param("postId") long postId);

	@Query("SELECT p FROM Post p")
	List<Post> findAllPosts(String username);

	@Query("SELECT p FROM Post p WHERE p.posterAccount.username = :username")
	List<Post> findAllPostByUsername(String username);

	@Query("SELECT p FROM Post p WHERE p.group.groupId = :groupId")
	List<Post> findPostsByGroupId(long groupId);

	@Query(value = "Select p from Post p where p.posterAccount.username = :username "
			+ "AND (p.mode.modeId = 1 OR p.mode.modeId = 2 OR p.mode.modeId = 3) "
			+ "AND p.isDeleted = false "
			//+ "AND p.group.groupId = -1 "
			+ "ORDER BY p.postTimeAt desc")
	List<Post> findAllPostByUsernameOrderByPostTimeAtDesc(String username);
	
	@Query(value = "Select p from Post p where p.posterAccount.username = :username "
			+ "AND (p.mode.modeId = 1 OR p.mode.modeId = 2) "
			+ "AND p.isDeleted = false "
			//+ "AND p.group.groupId = -1 "
			+ "ORDER BY p.postTimeAt desc")
	List<Post> findAllPostOfFriendByUsernameOrderByPostTimeAtDesc(String username);
	
	@Query(value = "Select p from Post p "
			+ "JOIN Account_SocialGroup a ON p.group.groupId = a.id.groupId "
			+ "where a.id.username = :username "
			+ "AND a.isAccepted = true "
			+ "AND (p.mode.modeId = 1 OR p.mode.modeId = 2 OR p.mode.modeId = 3) "
			//+ "AND p.group.groupId != -1 "
			+ "AND p.isDeleted = false "
			+ "ORDER BY p.postTimeAt desc ")
	List<Post> findPostInGroupsByUsername(String username);
}
