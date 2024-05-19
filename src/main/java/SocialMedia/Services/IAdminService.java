package SocialMedia.Services;

import SocialMedia.Entities.Account;

public interface IAdminService {
	void unbanAccount(Account account);
	void banAccount(Account account);
}
