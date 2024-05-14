package SocialMedia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.SocialGroup;
import SocialMedia.Repositories.SocialGroupRepository;

@Service
public class SocialGroupServiceImpl implements ISocialGroupService{
	@Autowired
	SocialGroupRepository socialGroupRepository;
	
	@Override
	public Optional<SocialGroup> findByGroupId(long groupId) {
		return socialGroupRepository.findByGroupId(groupId);
	}
}
