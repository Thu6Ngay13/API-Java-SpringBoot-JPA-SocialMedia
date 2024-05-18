package SocialMedia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Like;
import SocialMedia.Entities.LikeId;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

}
