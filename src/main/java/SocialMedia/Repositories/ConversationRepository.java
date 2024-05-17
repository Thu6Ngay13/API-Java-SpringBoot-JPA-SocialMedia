package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{
	
	@Query("SELECT c FROM Conversation c JOIN c.accounts a WHERE c.conversationId = :conversationId AND a.username LIKE :username")
	Optional<Conversation> checkUserInConversation(@Param("conversationId") long conversationId, @Param("username") String username);
	
	@Query("SELECT c FROM Conversation c JOIN c.accounts a WHERE a.username LIKE :username")
	List<Conversation> findAllConversations(@Param("username") String username);

	@Query("SELECT c.conversationId FROM Conversation c "
			+ "JOIN c.accounts a "
			+ "WHERE a.username IN (:username1, :username2) "
			+ "GROUP BY c.conversationId "
			+ "HAVING COUNT(DISTINCT a.username) = 2 "
				+ "AND COUNT(*) = 2")
	Optional<Long> findConversationIdWithFriend(@Param("username1") String username1, @Param("username2") String username2);
	
}
