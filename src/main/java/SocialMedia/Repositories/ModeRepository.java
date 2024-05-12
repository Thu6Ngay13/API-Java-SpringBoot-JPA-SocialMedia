package SocialMedia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Comment;
@Repository
public interface ModeRepository extends JpaRepository<Comment, Long>{
 
}