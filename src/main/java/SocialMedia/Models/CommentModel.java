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
public class CommentModel {
	private String avatar;
	private String username;
    private String fullName;
	private Long commentId;
	private String commentText;
	private String commentImage;
	private Boolean isDeleted = false;
	private String commentTimeAt;
	private Long postId;
	
}
