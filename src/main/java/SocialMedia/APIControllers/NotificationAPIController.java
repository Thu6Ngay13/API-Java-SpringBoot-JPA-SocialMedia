package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Notification;
import SocialMedia.Models.NotificationModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.INotificationService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class NotificationAPIController {
	@Autowired
	INotificationService notificationService;
	
	@GetMapping("/notification/{username}")
	public ResponseEntity<?> Home(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request, 
			Model model) {
		
		List<Notification> notifications = notificationService.findAllNotificationReceipts(username);
		List<NotificationModel> notificationModels = new ArrayList<>();
		
		for (Notification notification : notifications) {
			NotificationModel notificationModel = new NotificationModel();
			notificationModel.setAvatar(notification.getAccountCreate().getAvatarURL());
			notificationModel.setUsername(notification.getAccountCreate().getUsername());
			notificationModel.setFullName(notification.getAccountCreate().getFullname());
			notificationModel.setContent(notification.getContent());
			notificationModel.setIsSeen(notification.isSeen());
			notificationModel.setNotifyTimeAt(notification.getNotificationTimeAt().toString());
			notificationModels.add(notificationModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", notificationModels), 
				HttpStatus.OK
		);
	}
}
