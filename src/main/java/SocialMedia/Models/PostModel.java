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
public class PostModel {
	private long postId;
    private String avatar;
    private String username;
    private String fullName;
    private String postingTimeAt;
    private long mode;
    private String postText;
    private String postMedia;
    private boolean liked;
    private long groupId = -1;
}
