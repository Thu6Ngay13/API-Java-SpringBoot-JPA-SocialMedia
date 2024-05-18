package SocialMedia.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Comment;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long>{
	@Query("SELECT c FROM Comment c JOIN c.post p WHERE p.postId = :postId and c.isDeleted = false")
	List<Comment> findCommentsByPostId(Long postId); 
}