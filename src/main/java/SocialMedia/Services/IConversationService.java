package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import SocialMedia.Entities.Conversation;

public interface IConversationService {
	Optional<Conversation> findById(Long id);
	Optional<Conversation> checkUserInConversation(long conversationId, String username);
	
	List<Conversation> findAllConversations(String username);
	Optional<Long> findConversationIdWithFriend(String username1, String username2);
	<S extends Conversation> S save(S entity);
}
