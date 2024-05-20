package SocialMedia.Services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;
import SocialMedia.Entities.Report;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.PostRepository;
import SocialMedia.Repositories.ReportRepository;

@Service
public class AdminServiceImpl implements IAdminService{
	
	@Autowired 
	AccountRepository accountRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ReportRepository reportRepository;
	
	
	@Override
	public void unbanAccount(Account account) {
		account.setBanned(false);
		accountRepository.save(account);
	}
	
	@Override
	public void banAccount(Account account) {
		account.setBanned(true);
		accountRepository.save(account);
	}

	@Override
	public void deletePost(Post post) {
		post.setDeleted(true);
		postRepository.save(post);
	}

	@Override
	public void ignoreAllReportOfPost(long postId) {
		List<Report> reports = reportRepository.findAllReportsWithPostId(postId);
		for (Report report : reports) {
			Account reporter = report.getReporterAccount();
			Set<Report> reportOfReporter = reporter.getDoReports();
			reportOfReporter.remove(report);
			reporter.setDoReports(reportOfReporter);
			accountRepository.save(reporter);
			
			Post post = report.getPost();
			Set<Report> reportOfPost = post.getReports();
			reportOfPost.remove(report);
			post.setReports(reportOfPost);
			postRepository.save(post);
			
			reportRepository.delete(report);
		}
	}
	
	
}
