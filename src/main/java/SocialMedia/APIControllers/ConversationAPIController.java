package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Conversation;
import SocialMedia.Models.ConversationModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IConversationService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ConversationAPIController {
	@Autowired
	IConversationService conversationService;
	
	@GetMapping("/conversation/{username}")
	public ResponseEntity<Response> getConversationsWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request, 
			Model model){
		
		List<Conversation> conversations = conversationService.findAllConversations(username);
		List<ConversationModel> conversationModels = new ArrayList<>();
		
		for (Conversation conversation : conversations) {
			ConversationModel conversationModel = new ConversationModel();
			conversationModel.setConversationId(conversation.getConversationId());
			conversationModel.setConversationName(conversation.getConversationName());
			conversationModels.add(conversationModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", conversationModels), 
				HttpStatus.OK
		);
	}
}
