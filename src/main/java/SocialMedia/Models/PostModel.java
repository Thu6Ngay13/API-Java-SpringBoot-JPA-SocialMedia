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
    private String avatar;
    private String username;
    private String fullName;
    private String postingTimeAt;
    private int mode;
    private String postText;
    private String postImage;
    private boolean liked;
}
