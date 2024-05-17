package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import SocialMedia.Entities.Post;
import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query(value = "SELECT p FROM Post p "
			+ "JOIN Friend f ON p.posterAccount.username LIKE f.friendId.usernameYou OR p.posterAccount.username LIKE f.friendId.usernameFriend "
			+ "WHERE (p.posterAccount.username LIKE :username "
			+ "OR (p.posterAccount.username NOT LIKE :username AND (p.mode.modeId = 0 OR p.mode.modeId = 1))) "
			+ "AND p.isDeleted = false")
	List<Post> findPostOfNewFeedWithUsername(@Param("username") String username, Pageable pageable);

	Optional<Post> findByPostId(Integer id);
	
	@Query(value = "SELECT p FROM Post p "
			+ "JOIN Friend f ON p.posterAccount.username LIKE f.friendId.usernameYou OR p.posterAccount.username LIKE f.friendId.usernameFriend "
			+ "WHERE (p.posterAccount.username LIKE :username "
			+ "OR (p.posterAccount.username NOT LIKE :username AND (p.mode.modeId = 0 OR p.mode.modeId = 1))) "
			+ "AND p.isDeleted = false "
			+ "AND p.postId = :postId ")
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
	
}
