package SocialMedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Message;
import SocialMedia.Repositories.MessageRepository;

@Service
public class MessageServiceImpl implements IMessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public <S extends Message> S save(S entity) {
		return messageRepository.save(entity);
	}

	@Override
	public List<Message> findAllMessages(long conversationId) {
		return messageRepository.findAllMessages(conversationId);
	}

}
