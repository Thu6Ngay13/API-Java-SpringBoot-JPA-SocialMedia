package SocialMedia.APIControllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.SocialGroup;
import SocialMedia.Enums.TypeSearchEnum;
import SocialMedia.Models.SearchModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IFriendService;
import SocialMedia.Services.ISocialGroupService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/search")
public class SeachController {

	@Autowired
	IFriendService friendService;

	@Autowired
	ISocialGroupService socialGroupService;

	@GetMapping("/{username}")
	public ResponseEntity<?> getSuggestFriend(@PathVariable(value = "username") String username,
			HttpServletRequest request) {

		Set<Account> makeFriendSearchs = friendService.getSearchFriend(username);

		List<SearchModel> searchModels = new ArrayList<>();
		for (Account account : makeFriendSearchs) {
			SearchModel searchModel = new SearchModel();
			searchModel.setViewType(TypeSearchEnum.MAKE_FRIEND);
			searchModel.setAvatar(account.getAvatarURL());
			searchModel.setUsername(account.getUsername());
			searchModel.setFullName(account.getFullname());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(-1);
			searchModels.add(searchModel);
		}

		Collections.shuffle(searchModels);
		return new ResponseEntity<Response>(new Response(true, "Thành công", searchModels), HttpStatus.OK);
	}

	@GetMapping("/{username}/{keyword}")
	public ResponseEntity<?> getSeach(@PathVariable(value = "username") String username,
			@PathVariable(value = "keyword") String keyword, HttpServletRequest request) {

		List<Account> yourFriendSearchs = friendService.searchAllYourFriends(username, keyword);
		List<Object[]> friendRequestSearchs = friendService.searchAllFriendRequests(username, keyword);
		List<Account> makeFriendSearchs = friendService.searchNotFriend(username, keyword);
		List<Account> makedFriendSearchs = friendService.searchMakedFriendSearchs(username, keyword);

		List<SocialGroup> joinedSocialGroupSearchs = socialGroupService.searchJoinedGroup(username, keyword);
		List<SocialGroup> joinSocialGroupSearchs = socialGroupService.searchJoinGroup(username, keyword);
		List<SocialGroup> unjoinSocialGroupSearchs = socialGroupService.searchUnjoinGroup(username, keyword);

		List<SearchModel> searchModels = new ArrayList<>();
		for (Account account : yourFriendSearchs) {
			SearchModel searchModel = new SearchModel();
			searchModel.setViewType(TypeSearchEnum.YOUR_FRIEND);
			searchModel.setAvatar(account.getAvatarURL());
			searchModel.setUsername(account.getUsername());
			searchModel.setFullName(account.getFullname());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(-1);
			searchModels.add(searchModel);
		}

		for (Object[] temp : friendRequestSearchs) {
			SearchModel searchModel = new SearchModel();
			Account account = (Account) temp[0];
			LocalDateTime requestTimeAt = (LocalDateTime) temp[1];
			searchModel.setViewType(TypeSearchEnum.FRIEND_REQUEST);
			searchModel.setAvatar(account.getAvatarURL());
			searchModel.setUsername(account.getUsername());
			searchModel.setFullName(account.getFullname());
			searchModel.setRequestTimeAt(requestTimeAt.toString());
			searchModel.setGroupdId(-1);
			searchModels.add(searchModel);
		}

		for (Account account : makeFriendSearchs) {
			SearchModel searchModel = new SearchModel();
			searchModel.setViewType(TypeSearchEnum.MAKE_FRIEND);
			searchModel.setAvatar(account.getAvatarURL());
			searchModel.setUsername(account.getUsername());
			searchModel.setFullName(account.getFullname());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(-1);
			searchModels.add(searchModel);
		}

		for (Account account : makedFriendSearchs) {
			SearchModel searchModel = new SearchModel();
			searchModel.setViewType(TypeSearchEnum.MAKED_FRIEND);
			searchModel.setAvatar(account.getAvatarURL());
			searchModel.setUsername(account.getUsername());
			searchModel.setFullName(account.getFullname());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(-1);
			searchModels.add(searchModel);
		}

		for (SocialGroup socialGroup : joinedSocialGroupSearchs) {
			SearchModel searchModel = new SearchModel();
			searchModel.setViewType(TypeSearchEnum.JOINED_GROUP);
			searchModel.setAvatar(socialGroup.getAvatarURL());
			searchModel.setUsername("");
			searchModel.setFullName(socialGroup.getGroupName());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(socialGroup.getGroupId());
			searchModel.setGroupModeId(socialGroup.getMode().getModeId());
			searchModel.setGroupModeType(socialGroup.getMode().getModeType());
			searchModels.add(searchModel);
		}

		for (SocialGroup socialGroup : joinSocialGroupSearchs) {
			SearchModel searchModel = new SearchModel();
			if (socialGroup.getMode().getModeId() == 3) {
				searchModel.setViewType(TypeSearchEnum.JOIN_GROUP_PRIVATE);
			} else {
				searchModel.setViewType(TypeSearchEnum.JOIN_GROUP_PUBLIC);
			}
			searchModel.setAvatar(socialGroup.getAvatarURL());
			searchModel.setUsername("");
			searchModel.setFullName(socialGroup.getGroupName());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(socialGroup.getGroupId());
			searchModel.setGroupModeId(socialGroup.getMode().getModeId());
			searchModel.setGroupModeType(socialGroup.getMode().getModeType());
			searchModels.add(searchModel);
		}

		for (SocialGroup socialGroup : unjoinSocialGroupSearchs) {
			SearchModel searchModel = new SearchModel();
			if (socialGroup.getMode().getModeId() == 3) {
				searchModel.setViewType(TypeSearchEnum.UNJOIN_GROUP_PRIVATE);
			} else {
				searchModel.setViewType(TypeSearchEnum.UNJOIN_GROUP_PUBLIC);
			}
			searchModel.setAvatar(socialGroup.getAvatarURL());
			searchModel.setUsername("");
			searchModel.setFullName(socialGroup.getGroupName());
			searchModel.setRequestTimeAt("");
			searchModel.setGroupdId(socialGroup.getGroupId());
			searchModel.setGroupModeId(socialGroup.getMode().getModeId());
			searchModel.setGroupModeType(socialGroup.getMode().getModeType());
			searchModels.add(searchModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", searchModels), HttpStatus.OK);
	}

}
