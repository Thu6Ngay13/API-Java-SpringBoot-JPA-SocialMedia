package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p")
    List<Post> findAllPosts(String username);

	Optional<Post> findByPostId(Integer id);
	
	@Query("SELECT p FROM Post p WHERE p.posterAccount.username = :username")
    List<Post> findAllPostByUsername(String username);
	
	@Query(value = "SELECT COUNT(*) FROM Comment WHERE postId = :postId", nativeQuery = true)
    int countCommentsByPostId(@Param("postId") int postId);
	
	@Override
	default Post getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
