package SocialMedia.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Repositories.LikeRepository;

@Service
public class LikeServiceImpl implements ILikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
}
