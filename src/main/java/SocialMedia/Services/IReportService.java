package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Report;

public interface IReportService {

	List<Report> findAllReport();

	void reportPort(String username, long postId, String content);

	void handleReport(long postId);

	List<Report> findAllReportsWithPostId(long postId);
}
