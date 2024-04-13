package SocialMedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Notification;
import SocialMedia.Repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements INotificationService{

	@Autowired
	NotificationRepository notificationRepository;
	
	@Override
	public List<Notification> findAllNotificationReceipts(String username) {
		return notificationRepository.findAllNotificationReceipts(username);
	}
}
