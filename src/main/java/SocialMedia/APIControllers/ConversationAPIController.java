package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Conversation;
import SocialMedia.Models.ConversationModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IConversationService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/conversation")
public class ConversationAPIController {
	@Autowired
	IConversationService conversationService;
	
	@GetMapping("/{username}")
	public ResponseEntity<Response> getConversationsWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request){
		
		List<Conversation> conversations = conversationService.findAllConversations(username);
		List<ConversationModel> conversationModels = new ArrayList<>();
		
		for (Conversation conversation : conversations) {
			String conversationAvatar = "";
			ConversationModel conversationModel = new ConversationModel();
			conversationModel.setConversationId(conversation.getConversationId());
			conversationModel.setConversationAvatar(conversationAvatar);
			conversationModel.setConversationName(conversation.getConversationName());
			conversationModels.add(conversationModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", conversationModels), 
				HttpStatus.OK
		);
	}
}
