package SocialMedia.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Notification;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.NotificationRepository;

@Service
public class NotificationServiceImpl implements INotificationService{

	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	
	@Override
	public List<Notification> findAllNotificationReceipts(String username) {
		return notificationRepository.findAllNotificationReceipts(username);
	}

	public void createNotification(String usernameCreate, List<String> usernameReceipts, String content) {

		Optional<Account> account1 = accountRepository.findByUsername(usernameCreate);
		List<Optional<Account>> account2s = new ArrayList<>();
		
		for(int i = 0; i < usernameReceipts.size(); i++) {
			Optional<Account> account2 = accountRepository.findByUsername(usernameReceipts.get(i));
			account2s.add(account2);
		}
		
		if (account1.isPresent() && account2s.size() > 0) {
			Account accountCreate = account1.get();
			Notification notification = new Notification();
			notification.setText(content);
			notification.setNotificationTimeAt(LocalDateTime.now());
			notification.setSeen(false);
			notification.setAccountCreate(accountCreate);
			
			HashSet<Account> accountReceipts = new HashSet<>();
			for (int i = 0; i < account2s.size(); i++) {
				Optional<Account> account2 = account2s.get(i);
				if (account2.isPresent()) {
					Account accountReceipt = account2.get();
					accountReceipts.add(accountReceipt);
					
					Set<Notification> notificationReceipts = accountReceipt.getNotificationReceipts();
					notificationReceipts.add(notification);
					accountReceipt.setNotificationReceipts(notificationReceipts);
					accountRepository.save(accountReceipt);
				}
			}
			
			notification.setAccountReceipts(accountReceipts);
			notificationRepository.save(notification);
			
			Set<Notification> notificationCreates = accountCreate.getNotificationCreates();
			notificationCreates.add(notification);
			accountCreate.setNotificationCreates(notificationCreates);
			accountRepository.save(accountCreate);
			
		}
	}

}
