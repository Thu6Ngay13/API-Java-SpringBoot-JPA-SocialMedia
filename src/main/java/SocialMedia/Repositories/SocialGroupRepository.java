package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SocialMedia.Entities.SocialGroup;

@Repository
public interface SocialGroupRepository  extends JpaRepository<SocialGroup, Long>{
	Optional<SocialGroup> findByGroupId(long groupId);
	List<SocialGroup> findByGroupNameContainingIgnoreCase(String searchString);
}
