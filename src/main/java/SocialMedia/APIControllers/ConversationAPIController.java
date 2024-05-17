package SocialMedia.APIControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Conversation;
import SocialMedia.Models.ConversationModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IConversationService;
import SocialMedia.Services.IFriendService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/conversation")
public class ConversationAPIController {

	@Autowired
	IConversationService conversationService;

	@Autowired
	IFriendService friendService;

	@Autowired
	IAccountService accountService;

	@GetMapping("/{username}")
	public ResponseEntity<Response> getConversationsWithUsername(@PathVariable(value = "username") String username,
			HttpServletRequest request) {

		List<Conversation> conversations = conversationService.findAllConversations(username);
		List<ConversationModel> conversationModels = new ArrayList<>();

		for (Conversation conversation : conversations) {
			
			ConversationModel conversationModel = new ConversationModel();
			conversationModel.setConversationId(conversation.getConversationId());
			
			Set<Account> accounts = conversation.getAccounts();
			Iterator<Account> iterator = accounts.iterator();
			Account accountInviter = null;
			
			while (iterator.hasNext()) {
				accountInviter = iterator.next();
				if(!accountInviter.getUsername().equals(username)) break;	
			}
			
			conversationModel.setConversationAvatar(accountInviter.getAvatarURL());
			if(accounts.size() == 2) {
				conversationModel.setConversationName(accountInviter.getFullname());
			}
			else {
				conversationModel.setConversationName(conversation.getConversationName());
			}
			
			conversationModels.add(conversationModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", conversationModels), HttpStatus.OK);
	}

	@GetMapping("/{username1}/withfriend/{username2}")
	public ResponseEntity<Response> getConversationsWithFriend(@PathVariable(value = "username1") String username1,
			@PathVariable(value = "username2") String username2, HttpServletRequest request) {

		Optional<Account> account1 = friendService.checkIsFriend(username1, username2);
		Optional<Account> account2 = accountService.findByUsername(username1);
		
		if (account1.isPresent() && account2.isPresent()) {
			
			Account accountFriend = account1.get();
			Account accountYou = account2.get();		
			
			ConversationModel conversationModel = new ConversationModel();
			conversationModel.setConversationAvatar(accountFriend.getAvatarURL());
			conversationModel.setConversationName(accountFriend.getFullname());

			Optional<Long> conversationIds = conversationService.findConversationIdWithFriend(username1, username2);
			if (conversationIds.isPresent()) {
				long conversationId = conversationIds.get();
				conversationModel.setConversationId(conversationId);

				List<ConversationModel> conversationModels = new ArrayList<>();
				conversationModels.add(conversationModel);

				return new ResponseEntity<Response>(new Response(true, "Thành công", conversationModels),
						HttpStatus.OK);
			} else {
				Set<Account> accounts = new HashSet<>();
				accounts.add(accountFriend);
				accounts.add(accountYou);

				Conversation conversation = new Conversation();
				conversation.setCreationTimeAt(LocalDateTime.now());
				conversation.setConversationName("");
				conversation.setDeleted(false);
				conversation.setAccounts(accounts);
				conversationService.save(conversation);

				Set<Conversation> conversationFriend = accountFriend.getConversations();
				Set<Conversation> conversationYou = accountYou.getConversations();

				conversationFriend.add(conversation);
				conversationYou.add(conversation);

				accountFriend.setConversations(conversationFriend);
				accountYou.setConversations(conversationYou);

				accountService.save(accountFriend);
				accountService.save(accountYou);

				conversationModel.setConversationId(conversation.getConversationId());
				List<ConversationModel> conversationModels = new ArrayList<>();
				conversationModels.add(conversationModel);
				
				return new ResponseEntity<Response>(new Response(true, "Thành công", conversationModels),
						HttpStatus.OK);
			}
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}
}
