package SocialMedia.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Mode;
import SocialMedia.Repositories.ModeRepository;

@Service
public class ModeServiceImpl implements IModeService {
	
	@Autowired
	ModeRepository modeRepository;
	
	@Override
	public Optional<Mode> findByModeId(long modeId) {
		return modeRepository.findByModeId(modeId);
	}
}
