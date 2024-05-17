package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Post;
import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p")
    List<Post> findAllPosts(String username);

	Optional<Post> findByPostId(Integer id);
	
	@Query("SELECT p FROM Post p WHERE p.posterAccount.username = :username")
    List<Post> findAllPostByUsername(String username);
	
	@Query("SELECT p FROM Post p WHERE p.group.groupId = :groupId")
    List<Post> findPostsByGroupId(long groupId);
	
	@Query(value = "SELECT COUNT(*) FROM Comment WHERE postId = :postId", nativeQuery = true)
    int countCommentsByPostId(@Param("postId") long postId);
	
	
	@Query("SELECT p FROM Post p WHERE p.posterAccount.username = :username")
    List<Post> findPostInGroupsByUsername(String username);
	
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Account_share_Post (username, post_id) VALUES (:username, :postId)", nativeQuery = true)
    void share(@Param("username") String username, @Param("postId") long postId);
    
	@Query(value = "SELECT COUNT(*) FROM Account_share_Post WHERE username = :username and post_id = :postId", nativeQuery = true)
    int existAccountsharePost(@Param("username") String username, @Param("postId") long postId);
	
	@Query(value = "Select p from Post p where p.posterAccount.username = :username ORDER BY p.postTimeAt desc")
	List<Post> findAllPostByUsernameOrderByPostTimeAtDesc(String username);
}
