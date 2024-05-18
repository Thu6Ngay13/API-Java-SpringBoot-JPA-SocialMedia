package SocialMedia.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportModel {
	private long reportId;
	private String reportingTimeAt;
	private String text;
	private PostModel postModel;
	
}
