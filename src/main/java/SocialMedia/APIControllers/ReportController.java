package SocialMedia.APIControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Entities.Report;
import SocialMedia.Models.ReportModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IReportService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
	IReportService reportService;
	
	@GetMapping("/{username}/report/{postId}")
	public ResponseEntity<?> getPostOfNewFeedWithUsername(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, HttpServletRequest request) {

		List<Report> reports = reportService.findAll();
		List<ReportModel> reportModels = new ArrayList<>();
		
		for (Report report : reports) {
			ReportModel reportModel = new ReportModel();
			reportModel.setReportId(report.getReportId());
			reportModel.setText(report.getText());;
			reportModel.setReportingTimeAt(report.getReportingTimeAt().toString());
			
			reportModels.add(reportModel);
		}
		
		return new ResponseEntity<Response>(new Response(true, "Thành công", reportModels), HttpStatus.OK);
	}

}
