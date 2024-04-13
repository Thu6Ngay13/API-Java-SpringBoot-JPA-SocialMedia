package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	@Query("SELECT a.notificationReceipts FROM Account a WHERE a.username = :username")
    List<Notification> findAllNotificationReceipts(String username);
	
}
