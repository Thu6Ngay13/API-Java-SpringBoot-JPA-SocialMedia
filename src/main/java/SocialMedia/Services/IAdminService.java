package SocialMedia.Services;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;

public interface IAdminService {
	void unbanAccount(Account account);
	void banAccount(Account account);
	void deletePost(Post post);
	void ignoreAllReportOfPost(long postId);
}
