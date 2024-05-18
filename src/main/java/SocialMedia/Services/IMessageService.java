package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Message;

public interface IMessageService {
	<S extends Message> S save(S entity);
	List<Message> findAllMessages(long conversationId);
}
