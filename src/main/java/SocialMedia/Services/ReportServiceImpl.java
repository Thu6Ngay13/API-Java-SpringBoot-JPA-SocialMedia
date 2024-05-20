package SocialMedia.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.Post;
import SocialMedia.Entities.Report;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.PostRepository;
import SocialMedia.Repositories.ReportRepository;

@Service
public class ReportServiceImpl implements IReportService{

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public List<Report> findAllReport() {
		return reportRepository.findAllReport();
	}
	
	@Override
	public List<Report> findAllReportsWithPostId(long postId) {
		return reportRepository.findAllReportsWithPostId(postId);
	}

	@Override
	public void reportPort(String username, long postId, String content) {
		Optional<Post> post = postRepository.findById(postId);
		Optional<Account> account = accountRepository.findByUsername(username);
		
		if (post.isPresent() && account.isPresent()) {
			Report report = new Report();
			report.setPost(post.get());
			report.setReportingTimeAt(LocalDateTime.now());
			report.setContent(content);
			report.setIsHandled(false);	
			reportRepository.save(report);
		}
	}

	@Override
	public void handleReport(long postId) {
		// TODO Auto-generated method stub
		
	}	

}
