package SocialMedia.Services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Account_SocialGroup;
import SocialMedia.Entities.Account_SocialGroup_id;
import SocialMedia.Entities.Mode;
import SocialMedia.Entities.Notification;
import SocialMedia.Entities.SocialGroup;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.Account_SocialGroupRepository;
import SocialMedia.Repositories.ModeRepository;
import SocialMedia.Repositories.NotificationRepository;
import SocialMedia.Repositories.SocialGroupRepository;

@Service
public class SocialGroupServiceImpl implements ISocialGroupService {

	@Autowired
	SocialGroupRepository socialGroupRepo;
	@Autowired
	Account_SocialGroupRepository accGroupRepo;
	@Autowired
	ModeRepository modeRepo;
	@Autowired
	AccountRepository accRepo;
	@Autowired
	NotificationRepository notiRepo;

	@Override
	public Optional<SocialGroup> findByGroupId(long groupId) {
		return socialGroupRepo.findByGroupId(groupId);
	}

	@Override
	public void save(SocialGroup socialGroup) {
		socialGroupRepo.save(socialGroup);
	}

	@Override
	public List<SocialGroup> findByGroupsByName(String searchString) {
		return socialGroupRepo.findByGroupNameContainingIgnoreCase(searchString);
	}

	@Override
	public void sendRequestToGroup(Account account, SocialGroup group) {
		Account_SocialGroup accGroup = new Account_SocialGroup();
		accGroup.setId(new Account_SocialGroup_id(group.getGroupId(), account.getUsername()));
		accGroup.setAccepted(false);
		accGroupRepo.save(accGroup);
/*
		Notification noti = new Notification();
		noti.setNotificationTimeAt((LocalDateTime.now()));
		noti.setText(account.getFullname() + " vừa gửi yêu cầu vào nhóm " + group.getGroupName());
		Set<Account> listAcc = new HashSet<>();
		listAcc.add(account);
		noti.setAccountReceipts(listAcc);
		noti.setAccountCreate(account);
		notiRepo.save(noti);*/
	}

	@Override
	public void acceptMember(String username, long groupId) {
		Account_SocialGroup accGroup = accGroupRepo.findOne(username, groupId);
		accGroup.setAccepted(true);
		accGroupRepo.save(accGroup);
/*
		Notification noti = new Notification();
		noti.setNotificationTimeAt((LocalDateTime.now()));
		Optional<SocialGroup> optionalGroup = socialGroupRepo.findByGroupId(groupId);
		SocialGroup group = optionalGroup.orElse(null);
		noti.setText("Bạn đã được chấp nhận vào nhóm " + group.getGroupName());

		Set<Account> listAcc = new HashSet<>();
		Optional<Account> optionalAccount = accRepo.findByUsername(username);
		Account account = optionalAccount.orElse(null);
		listAcc.add(account);
		noti.setAccountReceipts(listAcc);
		notiRepo.save(noti);*/
	}

	@Override
	public List<Account_SocialGroup> findAccountSocialGroupByUsername(String username) {
		return accGroupRepo.findAccountSocialGroupByUsername(username);
	}

	@Override
	public List<Account_SocialGroup> findAccountSocialGroupByGroupId(long groupId) {
		return accGroupRepo.findAccountSocialGroupByGroupId(groupId);
	}

	@Override
	public List<Account_SocialGroup> findAllAccountSocialGroup() {
		return accGroupRepo.findAll();
	}
	
	@Override
	public Account_SocialGroup findOneAccountSocialGroup(String username, long groupId) {
		return accGroupRepo.findOne(username, groupId);
	}

	@Override
	public boolean createGroup(String username, String groupName, long modeId, String description) {
		List<SocialGroup> listGroup = socialGroupRepo.findByGroupNameContainingIgnoreCase(groupName);
		for (SocialGroup group : listGroup) {
			if (group.getGroupName().equals(groupName))
				return false;
		}
		SocialGroup newGroup = new SocialGroup();
		Mode mode = new Mode();
		mode = modeRepo.findById(modeId).get();
		newGroup.setMode(mode);
		newGroup.setGroupName(groupName);
		Optional<Account> optionalAccount = accRepo.findByUsername(username);
		Account account = optionalAccount.orElse(null);
		newGroup.setHolderAccount(account);
		newGroup.setCreationTimeAt(LocalDateTime.now());
		newGroup.setAvatarURL(
				"https://png.pngtree.com/element_our/20200610/ourlarge/pngtree-social-networking-image_2239654.jpg");
		newGroup.setDeleted(false);
		newGroup.setDescription(description);
		socialGroupRepo.save(newGroup);
		this.sendRequestToGroup(account, newGroup);
		this.acceptMember(username, newGroup.getGroupId());
		return true;
	}
	
	@Override
	public boolean updateGroup(long groupId, String username, String groupName, long modeId, String description, String groupImage) {

		Optional<SocialGroup> optionalGroup = socialGroupRepo.findByGroupId(groupId);
		SocialGroup group = optionalGroup.orElse(null);
		
		Mode mode = new Mode();
		mode = modeRepo.findById(modeId).get();
		group.setMode(mode);
		group.setGroupName(groupName);
		group.setDescription(description);
		group.setAvatarURL(groupImage);
		socialGroupRepo.save(group);
		
		List<SocialGroup> listGroup = socialGroupRepo.findByGroupNameContainingIgnoreCase(groupName);
		for (SocialGroup gr : listGroup) {
			if (gr.getGroupName().equals(groupName))
			{
				if (groupName == group.getGroupName())
					return true;
				else return false;
			}
		}
		return true;
	}

	@Override
	public void leaveGroup(String username, long groupId) {
		Account_SocialGroup accGroup = accGroupRepo.findOne(username, groupId);

	/*	Notification noti = new Notification();
		noti.setNotificationTimeAt((LocalDateTime.now()));

		Optional<SocialGroup> optionalGroup = socialGroupRepo.findByGroupId(groupId);
		SocialGroup group = optionalGroup.orElse(null);
		noti.setText("Bạn không còn là thành viên của nhóm " + group.getGroupName());

		Set<Account> listAcc = new HashSet<>();
		Optional<Account> optionalAccount = accRepo.findByUsername(username);
		Account account = optionalAccount.orElse(null);
		listAcc.add(account);
		noti.setAccountReceipts(listAcc);
		notiRepo.save(noti);
*/
		accGroupRepo.delete(accGroup);
	}

	@Override
	public boolean deleteGroup(String username, long groupId) {
		Optional<Account> optionalAccount = accRepo.findByUsername(username);
		Account account = optionalAccount.orElse(null);

		Optional<SocialGroup> optionalGroup = socialGroupRepo.findByGroupId(groupId);
		SocialGroup group = optionalGroup.orElse(null);
		if (group.getHolderAccount() == account) {
			group.setDeleted(true);
			socialGroupRepo.save(group);
/*
			Notification noti = new Notification();
			noti.setNotificationTimeAt((LocalDateTime.now()));
			noti.setText("Group " + group.getGroupName() + "đã bị xóa!!!");
			noti.setAccountReceipts(group.getJoinedAccounts());
			notiRepo.save(noti);*/
			return true;
		}
		return false;

	}

	@Override
	public List<SocialGroup> findByGroupNameContainingIgnoreCase(String searchString) {
		return socialGroupRepo.findByGroupNameContainingIgnoreCase(searchString);
	}

	@Override
	public List<SocialGroup> searchJoinedGroup(String username, String searchString) {
		return socialGroupRepo.searchJoinedGroup(username, searchString);
	}

	@Override
	public List<SocialGroup> searchJoinGroup(String username, String searchString) {
		return socialGroupRepo.searchJoinGroup(username, searchString);
	}

	@Override
	public List<SocialGroup> searchUnjoinGroup(String username, String searchString) {
		return socialGroupRepo.searchUnjoinGroup(username, searchString);
	}

	@Override
	public Optional<SocialGroup> findGroupByUsernameAndGroupId(String username, long groupId) {
		return socialGroupRepo.findGroupByUsernameAndGroupId(username, groupId);
	}

	@Override
	public void joinGroup(String username, long groupId) {
		Account_SocialGroup_id account_SocialGroup_id = new Account_SocialGroup_id(groupId, username);
		Account_SocialGroup account_SocialGroup = new Account_SocialGroup(account_SocialGroup_id, false);
		accGroupRepo.save(account_SocialGroup);
	}

	@Override
	public void unjoinGroup(String username, long groupId) {
		Account_SocialGroup_id account_SocialGroup_id = new Account_SocialGroup_id(groupId, username);
		Optional<Account_SocialGroup> account_SocialGroup = accGroupRepo.findById(account_SocialGroup_id);
		if (account_SocialGroup.isPresent()) {
			Account_SocialGroup as  = account_SocialGroup.get();
			accGroupRepo.delete(as);
		}
		
	}

	@Override
	public Set<SocialGroup> getSearchGroup(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Account> listAcceptMemberGroup(long groupId) {
		return socialGroupRepo.listAcceptMemberGroup(groupId);
	}
}
