package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Notification;

public interface INotificationService {
	List<Notification> findAllNotificationReceipts(String username);
}
