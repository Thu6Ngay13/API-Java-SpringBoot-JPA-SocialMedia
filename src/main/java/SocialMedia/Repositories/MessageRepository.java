package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query("SELECT m FROM Message m WHERE m.conversation.conversationId = :conversationId")
	List<Message> findAllMessages(long conversationId);
	
}