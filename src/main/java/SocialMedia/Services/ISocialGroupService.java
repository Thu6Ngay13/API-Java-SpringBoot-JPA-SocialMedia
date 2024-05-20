package SocialMedia.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.query.Param;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Account_SocialGroup;
import SocialMedia.Entities.SocialGroup;

public interface ISocialGroupService {
	Optional<SocialGroup> findByGroupId(long groupId);

	public void save(SocialGroup group);
	
	Account_SocialGroup findOneAccountSocialGroup(String username, long groupId);

	List<SocialGroup> findByGroupsByName(String searchString);

	List<Account_SocialGroup> findAccountSocialGroupByUsername(String username);

	List<Account_SocialGroup> findAccountSocialGroupByGroupId(long groupId);

	List<Account_SocialGroup> findAllAccountSocialGroup();

	void sendRequestToGroup(Account account, SocialGroup group);

	void acceptMember(String username, long groupId);

	boolean createGroup(String username, String groupName, long modeId, String description);
	
	boolean updateGroup(long groupId, String username, String groupName, long modeId, String description, String groupImage);

	void leaveGroup(String username, long groupId);

	boolean deleteGroup(String username, long groupId);

	List<SocialGroup> findByGroupNameContainingIgnoreCase(String searchString);
	
	List<SocialGroup> searchJoinedGroup(String username, String searchString);

	List<SocialGroup> searchJoinGroup(String username, String searchString);

	List<SocialGroup> searchUnjoinGroup(String username, String searchString);
	
	Optional<SocialGroup> findGroupByUsernameAndGroupId(String username, long groupId);

	void joinGroup(String username, long groupId);

	void unjoinGroup(String username, long groupId);

	Set<SocialGroup> getSearchGroup(String username);
	
	Set<Account> listAcceptMemberGroup(long groupId);
}
