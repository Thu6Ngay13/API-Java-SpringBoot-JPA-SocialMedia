package SocialMedia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SocialMedia.Entities.Mode;


@Repository
public interface ModeRepository extends JpaRepository<Mode, Long> {
	Optional<Mode> findByModeId(long modeId);
}