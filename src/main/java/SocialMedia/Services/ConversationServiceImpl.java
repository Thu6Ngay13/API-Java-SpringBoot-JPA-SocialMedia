package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Conversation;
import SocialMedia.Repositories.ConversationRepository;

@Service
public class ConversationServiceImpl implements IConversationService {

	@Autowired
	ConversationRepository conversationRepository;
	
	@Override
	public Optional<Conversation> findById(Long id) {
		return conversationRepository.findById(id);
	}
	
	@Override
	public Optional<Conversation> checkUserInConversation(long conversationId, String username) {
		return conversationRepository.checkUserInConversation(conversationId, username);
	}

	public List<Conversation> findAllConversations(String username){
		return conversationRepository.findAllConversations(username);
	}
}
	