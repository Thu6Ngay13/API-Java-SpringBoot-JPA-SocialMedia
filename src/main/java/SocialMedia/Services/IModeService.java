package SocialMedia.Services;

import java.util.Optional;

import SocialMedia.Entities.Mode;

public interface IModeService {
	Optional<Mode> findByModeId(long modeId);
}
