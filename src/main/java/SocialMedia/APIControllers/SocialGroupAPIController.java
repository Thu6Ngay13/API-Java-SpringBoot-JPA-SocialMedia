package SocialMedia.APIControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Account_SocialGroup;
import SocialMedia.Entities.Mode;
import SocialMedia.Entities.Post;
import SocialMedia.Entities.SocialGroup;
import SocialMedia.Models.PostModel;
import SocialMedia.Models.SocialGroupModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.IModeService;
import SocialMedia.Services.IPostService;
import SocialMedia.Services.ISocialGroupService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/group")
public class SocialGroupAPIController {
	@Autowired
	IPostService postService;

	@Autowired
	IAccountService accountService;

	@Autowired
	IModeService modeService;

	@Autowired
	ISocialGroupService socialGroupService;

	@GetMapping("/groups/{username}")
	public ResponseEntity<?> getGroupsByUsername(@PathVariable(value = "username") String username) {
		List<SocialGroupModel> groupModels = new ArrayList<>();
		List<Account_SocialGroup> accGroups = new ArrayList<>();

		List<Account_SocialGroup> allGroups = socialGroupService.findAccountSocialGroupByUsername(username);
		for (Account_SocialGroup accGr : allGroups) {
			if (accGr.isAccepted() == true)
				accGroups.add(accGr);
		}
		for (Account_SocialGroup accGr : accGroups) {
			Optional<SocialGroup> optionalGroup = socialGroupService.findByGroupId(accGr.getId().getGroupId());
			SocialGroup group = optionalGroup.orElse(null);

			SocialGroupModel groupModel = new SocialGroupModel();
			groupModel.setGroupId(group.getGroupId());
			groupModel.setGroupName(group.getGroupName());
			groupModel.setAvatarURL(group.getAvatarURL());
			groupModel.setCreationTimeAt(group.getCreationTimeAt().toString());
			groupModel.setModeId(group.getMode().getModeId());
			groupModel.setHolderFullName(group.getHolderAccount().getFullname());
			groupModels.add(groupModel);

		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", groupModels), HttpStatus.OK);
	}

	@GetMapping("/postInOneGroup")
	public ResponseEntity<?> getPostByGroupId(@RequestParam("username") String username,
			@RequestParam("groupId") long groupId) {

		List<Post> posts = postService.findPostsByGroupId(groupId);
		List<PostModel> postModels = new ArrayList<>();

		for (Post post : posts) {

			PostModel postModel = new PostModel();

			postModel.setPostId(post.getPostId());
			postModel.setAvatar(post.getPosterAccount().getAvatarURL());
			postModel.setUsername(post.getPosterAccount().getUsername());
			postModel.setFullName(post.getPosterAccount().getFullname());
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			postModel.setMode(post.getMode().getModeId());
			postModel.setPostText(post.getText());
			postModel.setPostMedia(post.getMediaURL());
			postModel.setGroupId(post.getGroup().getGroupId());
			boolean liked = false;
			for (Account account : post.getAccountLikes()) {
				if (account.getUsername().equals(username)) {
					liked = true;
					break;
				}
			}

			postModel.setLiked(liked);
			postModels.add(postModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", postModels), HttpStatus.OK);
	}

	@GetMapping("/posts/{username}")
	public ResponseEntity<?> getPostInGroupsByUsername(@PathVariable(value = "username") String username,
			HttpServletRequest request) {

		List<Post> posts = postService.findPostInGroupsByUsername(username);
		List<PostModel> postModels = new ArrayList<>();

		for (Post post : posts) {

			PostModel postModel = new PostModel();

			postModel.setPostId(post.getPostId());
			postModel.setAvatar(post.getPosterAccount().getAvatarURL());
			postModel.setUsername(post.getPosterAccount().getUsername());
			postModel.setFullName(post.getPosterAccount().getFullname());
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			postModel.setMode(post.getMode().getModeId());
			postModel.setPostText(post.getText());
			postModel.setPostMedia(post.getMediaURL());
			postModel.setGroupId(post.getGroup().getGroupId());
			boolean liked = false;
			for (Account account : post.getAccountLikes()) {
				if (account.getUsername().equals(username)) {
					liked = true;
					break;
				}
			}

			postModel.setLiked(liked);
			postModels.add(postModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", postModels), HttpStatus.OK);
	}

	@PostMapping("/createGroup")
	public ResponseEntity<?> createGroup(@RequestParam("username") String username,
			@RequestParam("groupName") String groupName, @RequestParam("modeId") long modeId) {
		boolean success = socialGroupService.createGroup(username, groupName, modeId);

		if (success) {
			SocialGroupModel groupModel = new SocialGroupModel();
			List<SocialGroup> listGroup = socialGroupService.findByGroupNameContainingIgnoreCase(groupName);
			for (SocialGroup group : listGroup) {
				groupModel.setGroupId(group.getGroupId());
				groupModel.setGroupName(group.getGroupName());
				groupModel.setAvatarURL(group.getAvatarURL());
				groupModel.setCreationTimeAt(group.getCreationTimeAt().toString());
				groupModel.setModeId(group.getMode().getModeId());
				groupModel.setHolderFullName(group.getHolderAccount().getFullname());
				groupModel.setHolderUsername(username);
			}
			return new ResponseEntity<Response>(new Response(true, "Thành công", groupModel), HttpStatus.OK);
		}

		else
			return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/searchGroup")
	public ResponseEntity<?> searchGroup(@RequestParam("groupName") String groupName) {
		List<SocialGroup> listGroup = socialGroupService.findByGroupNameContainingIgnoreCase(groupName);
		boolean empty = false;
		if (listGroup.isEmpty())
			empty = true;

		List<SocialGroupModel> groupModels = new ArrayList<>();
		for (SocialGroup gr : listGroup) {
			SocialGroupModel groupModel = new SocialGroupModel();
			groupModel.setGroupId(gr.getGroupId());
			groupModel.setGroupName(gr.getGroupName());
			groupModel.setAvatarURL(gr.getAvatarURL());
			groupModel.setCreationTimeAt(gr.getCreationTimeAt().toString());
			groupModel.setModeId(gr.getMode().getModeId());
			groupModel.setHolderFullName(gr.getHolderAccount().getFullname());
			groupModels.add(groupModel);

		}
		if (empty == false) {
			return new ResponseEntity<Response>(new Response(true, "Thành công", groupModels), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
		}
	}

	@GetMapping("/joinGroup")
	public ResponseEntity<?> joinGroup(@RequestParam("username") String username,
			@RequestParam("groupId") long groupId) {
		Optional<Account> optionalAccount = accountService.findByUsername(username);
		Account account = optionalAccount.orElse(null);
		Optional<SocialGroup> optionalGroup = socialGroupService.findByGroupId(groupId);
		SocialGroup gr = optionalGroup.orElse(null);

		socialGroupService.sendRequestToGroup(account, gr);
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

	@GetMapping("/outGroup")
	public ResponseEntity<?> outGroup(@RequestParam("username") String username,
			@RequestParam("groupId") long groupId) {
		// Optional<Account> optionalAccount = accountService.findByUsername(username);
		// Account account = optionalAccount.orElse(null);
		// Optional<SocialGroup> optionalGroup =
		// socialGroupService.findByGroupId(groupId);
		// SocialGroup gr = optionalGroup.orElse(null);

		socialGroupService.leaveGroup(username, groupId);
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

	@GetMapping("/removeMember")
	public ResponseEntity<?> removeMember(@RequestParam("username") String usernameRemove,
			@RequestParam("groupId") long groupId) {
		// Optional<Account> optionalAccount = accountService.findByUsername(username);
		// Account account = optionalAccount.orElse(null);
		// Optional<SocialGroup> optionalGroup =
		// socialGroupService.findByGroupId(groupId);
		// SocialGroup gr = optionalGroup.orElse(null);

		socialGroupService.leaveGroup(usernameRemove, groupId);
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

	@PostMapping("/createPost")
	public ResponseEntity<?> createPostInGroup(@RequestBody PostModel postModel) {
		if (postModel.getPostText() == "" && postModel.getPostMedia() == "") {
			return new ResponseEntity<Response>(new Response(false, "Create post false", null), HttpStatus.BAD_REQUEST);
		} else {
			Post post = new Post();
			post.setText(postModel.getPostText());
			post.setMediaURL(postModel.getPostMedia());

			// post.setPostTimeAt(LocalDateTime.parse(postModel.getPostingTimeAt()));
			post.setPostTimeAt(LocalDateTime.now());
			post.setDeleted(false);

			Optional<Account> optionalAccount = accountService.findByUsername(postModel.getUsername());
			Account posterAccount = optionalAccount.orElse(null);
			post.setPosterAccount(posterAccount);

			Optional<Mode> optionalMode = modeService.findByModeId(postModel.getMode());
			Mode mode = optionalMode.orElse(null);
			post.setMode(mode);

			Optional<SocialGroup> optionalSocialGroup = socialGroupService.findByGroupId(postModel.getGroupId());
			SocialGroup socialGroup = optionalSocialGroup.orElse(null);
			post.setGroup(socialGroup);
			postService.save(post);

			postModel.setPostId(post.getPostId());
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			return new ResponseEntity<Response>(new Response(true, "Thành công", postModel), HttpStatus.OK);
		}
	}
	
	@GetMapping("/{username}/viewgroup/{groupId}")
	public ResponseEntity<?> viewGroup(@PathVariable("username") String username,
			@PathVariable("groupId")long groupId) {
		
		Optional<SocialGroup> socialGroup = socialGroupService.findGroupByUsernameAndGroupId(username, groupId);
		if (socialGroup.isPresent()) {
			
			SocialGroup sg = socialGroup.get();
			List<SocialGroupModel> socialGroupModels = new ArrayList<>();
			
			SocialGroupModel socialGroupModel = new SocialGroupModel();
			socialGroupModel.setGroupId(sg.getGroupId());
			socialGroupModel.setAvatarURL(sg.getAvatarURL());
			socialGroupModel.setGroupName(sg.getGroupName());
			socialGroupModel.setCreationTimeAt(sg.getCreationTimeAt().toString());
			socialGroupModel.setModeId(sg.getMode().getModeId());
			
			socialGroupModel.setHolderFullName(sg.getHolderAccount().getFullname());
			socialGroupModel.setHolderUsername(sg.getHolderAccount().getUsername());
			
			socialGroupModels.add(socialGroupModel);
			return new ResponseEntity<Response>(new Response(true, "Thành công", socialGroupModels), HttpStatus.OK);
		}

		return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.OK);
	}
	
	@GetMapping("/{username}/joingroup/{groupId}")
	public ResponseEntity<?> joinGroupx(@PathVariable("username") String username,
			@PathVariable("groupId")long groupId) {

		Optional<SocialGroup> socialGroup = socialGroupService.findGroupByUsernameAndGroupId(username, groupId);
		if (socialGroup.isEmpty()) {
			socialGroupService.joinGroup(username, groupId);
		} 
		
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);

	}
	
	@GetMapping("/{username}/unjoingroup/{groupId}")
	public ResponseEntity<?> unjoinGroupx(@PathVariable("username") String username,
			@PathVariable("groupId")long groupId) {

		socialGroupService.unjoinGroup(username, groupId);
		
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}
}
