package SocialMedia.Services;

import java.util.List;

import SocialMedia.Entities.Report;

public interface IReportService {

	List<Report> findAll();

	void reportPort(String username, long postId, String text);

	void handleReport(long postId);
}
