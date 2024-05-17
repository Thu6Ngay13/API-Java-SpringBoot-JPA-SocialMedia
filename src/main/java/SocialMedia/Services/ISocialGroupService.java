package SocialMedia.Services;

import java.util.List;
import java.util.Optional;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Account_SocialGroup;
import SocialMedia.Entities.SocialGroup;

public interface ISocialGroupService {
	Optional<SocialGroup> findByGroupId(long groupId);
	
	public void save(SocialGroup group);

	List<SocialGroup> findByGroupsByName(String searchString);

	List<Account_SocialGroup> findAccountSocialGroupByUsername(String username);

	List<Account_SocialGroup> findAccountSocialGroupByGroupId(long groupId);

	List<Account_SocialGroup> findAllAccountSocialGroup();
	
	void sendRequestToGroup(Account account, SocialGroup group);

	void acceptMember(String username, long groupId);

	boolean createGroup(String username, String groupName, long modeId);

	void leaveGroup(String username, long groupId);
	
	boolean deleteGroup(String username, long groupId);
	
	List<SocialGroup> findByGroupNameContainingIgnoreCase(String searchString);
}
