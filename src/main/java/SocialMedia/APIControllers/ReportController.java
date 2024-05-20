package SocialMedia.APIControllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Response.Response;
import SocialMedia.Services.IReportService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
	IReportService reportService;

	@PostMapping("/{username}/report/{postId}")
	public ResponseEntity<?> reportPort(@PathVariable(value = "username") String username,
			@PathVariable(value = "postId") long postId, @RequestParam(value = "jsonBody") String jsonBody,
			HttpServletRequest request) {
		try {
			JSONObject jsonObject = new JSONObject(jsonBody);
			String reportContent = jsonObject.getString("contentReport");
			
			reportService.reportPort(username, postId, reportContent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Response>(new Response(true, "Thành công", null), HttpStatus.OK);
	}
}
