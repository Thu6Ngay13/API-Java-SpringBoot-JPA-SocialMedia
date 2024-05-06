package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{
	@Query("SELECT c FROM Conversation c JOIN c.accounts a WHERE a.username = :username")
	List<Conversation> findAllConversations(String username);
}
