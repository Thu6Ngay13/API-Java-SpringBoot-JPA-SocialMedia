package SocialMedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Conversation;
import SocialMedia.Repositories.ConversationRepository;

@Service
public class ConversationServiceImpl implements IConversationService {

	@Autowired
	ConversationRepository conversationRepository;
	
	public List<Conversation> findAllConversations(String username){
		return conversationRepository.findAllConversations(username);
	}
}
	