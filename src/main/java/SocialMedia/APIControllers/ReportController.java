package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Post;
import SocialMedia.Entities.Report;
import SocialMedia.Models.PostModel;
import SocialMedia.Models.ReportModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IReportService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
	IReportService reportService;

	@GetMapping("")
	public ResponseEntity<?> getAllReports(HttpServletRequest request) {
		List<Report> reports = reportService.findAll();
		List<ReportModel> reportModels = new ArrayList<>();

		for (Report report : reports) {
			ReportModel reportModel = new ReportModel();
			reportModel.setReportId(report.getReportId());
			reportModel.setText(report.getText());
			reportModel.setReportingTimeAt(report.getReportingTimeAt().toString());

			Post post = report.getPost();
			PostModel postModel = new PostModel();

			postModel.setAvatar(post.getPosterAccount().getAvatarURL());
			postModel.setFullName(post.getPosterAccount().getFullname());
			postModel.setUsername(post.getPosterAccount().getUsername());
			postModel.setGroupId(-1);
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			postModel.setPostId(post.getPostId());
			postModel.setPostMedia(post.getMediaURL());
			postModel.setPostText(post.getText());
			reportModel.setPostModel(postModel);

			reportModels.add(reportModel);
		}

		return new ResponseEntity<Response>(new Response(true, "Thành công", reportModels), HttpStatus.OK);
	}

	@PostMapping("/{username}/report/{postId}")
	public ResponseEntity<?> reportPort(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, @RequestParam(value = "jsonBody") String jsonBody,
			HttpServletRequest request) {
		reportService.reportPort(username, postId, jsonBody);
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

	@PostMapping("/handle/{reportId}")
	public ResponseEntity<?> handleReport(@PathVariable(value = "reportId") long reportId, HttpServletRequest request) {
		reportService.handleReport(reportId);
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}

}
