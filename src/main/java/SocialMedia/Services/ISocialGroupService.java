package SocialMedia.Services;

import java.util.Optional;
import SocialMedia.Entities.SocialGroup;

public interface ISocialGroupService {
	Optional<SocialGroup> findByGroupId(long groupId);
}
