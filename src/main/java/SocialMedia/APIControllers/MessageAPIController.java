package SocialMedia.APIControllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Conversation;
import SocialMedia.Entities.Message;
import SocialMedia.Enums.TypeMessageEnum;
import SocialMedia.Models.MessageModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IConversationService;
import SocialMedia.Services.IMessageService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/message")
public class MessageAPIController {

	@Autowired
	IConversationService conversationService;

	@Autowired
	IAccountService accountService;

	@Autowired
	IMessageService messageService;

	@GetMapping("/{conversationId}/{username}")
	public ResponseEntity<?> getAllMessagesWithUsernameAndConversationId(
			@PathVariable(value = "conversationId") long conversationId,
			@PathVariable(value = "username") String username, HttpServletRequest request) {

		Optional<Conversation> conversation = conversationService.checkUserInConversation(conversationId, username);
		if (!conversation.isPresent()) {
			return new ResponseEntity<Response>(new Response(true, "Không tồn tại cuộc hội thoại này!", null),
					HttpStatus.OK);
		}

		List<Message> messages = messageService.findAllMessages(conversationId);
		List<MessageModel> messageModels = new ArrayList<>();

		for (Message message : messages) {
			if (message.isDeleted())
				continue;

			MessageModel messageModel = new MessageModel();

			String usernameOfSender = message.getSenderAccount().getUsername();
			String mediaUrl = message.getMediaURL();

			messageModel.setFullname(usernameOfSender);
			messageModel.setUsername(message.getSenderAccount().getUsername());

			messageModel.setMessageId(message.getMessageId());
			messageModel.setConversationId(message.getConversation().getConversationId());

			messageModel.setText(message.getText());
			messageModel.setMediaURL(mediaUrl);
			messageModel.setMessageSendingAt(message.getMessageSendingAt().toString());
			messageModel.setSeen(message.isSeen());

			if (usernameOfSender.equals(username)) {
				if (mediaUrl.isEmpty())
					messageModel.setViewType(TypeMessageEnum.SENDER_MESSAGE);
				else
					messageModel.setViewType(TypeMessageEnum.SENDER_MEDIA);
			} else {
				if (mediaUrl.isEmpty())
					messageModel.setViewType(TypeMessageEnum.RECEIVER_MESSAGE);
				else
					messageModel.setViewType(TypeMessageEnum.RECEIVER_MEDIA);
			}

			messageModels.add(messageModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", messageModels), HttpStatus.OK);
	}

	@PostMapping("/sendmessage")
	public ResponseEntity<?> sendMessage(@RequestParam("jsonBody") String jsonBody, HttpServletRequest request) {

		try {
			JSONObject json = new JSONObject(jsonBody);

			String usernameOfSender = json.getString("username");
			Long conversationId = json.getLong("conversationId");

			Optional<Account> senderAccount = accountService.findByUsername(usernameOfSender);
			Optional<Conversation> conversation = conversationService.findById(conversationId);

			if (!senderAccount.isEmpty() || !conversation.isPresent()) {
				Message message = new Message();

				message.setText(json.getString("message"));
				message.setMediaURL("");

				message.setSeen(false);
				message.setDeleted(false);

				message.setMessageSendingAt(LocalDateTime.now());
				message.setSenderAccount(senderAccount.get());
				message.setConversation(conversation.get());

				messageService.save(message);
				return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response(true, "Thất bại", null), HttpStatus.OK);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(new Response(true, "Thất bại", null), HttpStatus.OK);
		}
	}

	@PostMapping("/sendmedia")
	public ResponseEntity<Response> sendMedia(@RequestParam("jsonBody") String jsonBody,
			@RequestParam("media") MultipartFile media, HttpServletRequest request) {

		try {
			System.out.println(jsonBody);

			File file = new File("D:/Lap_trinh_di_dong/File-SocialMedia/" + media.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file);

			fos.write(media.getBytes());
			fos.close();

			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(new Response(true, "Thất bại", null), HttpStatus.OK);
		}
	}
}
