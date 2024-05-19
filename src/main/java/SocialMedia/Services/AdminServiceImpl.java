package SocialMedia.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Repositories.AccountRepository;

@Service
public class AdminServiceImpl implements IAdminService{
	
	@Autowired 
	AccountRepository accountRepository;
	
	@Override
	public void unbanAccount(Account account) {
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 1" + account.isBanned());
		account.setBanned(false);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX 2" + account.isBanned());
		accountRepository.save(account);
	}
	
	@Override
	public void banAccount(Account account) {
		account.setBanned(true);
		accountRepository.save(account);
	}
	
	
}
