package SocialMedia.Models;

import SocialMedia.Enums.TypeReportPostEnum;
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
public class ReportPostModel {
	private TypeReportPostEnum viewType;
	private long reportId;
	private String fullname;
	private String reportingTimeAt;
	private String content;
	private PostModel postModel;
	
}
