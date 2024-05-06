package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Conversation;

public interface IConversationService {
	List<Conversation> findAllConversations(String username);
}
