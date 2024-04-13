package SocialMedia.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConversationModel {
	private int avatar;
    private String fullName;
    private String content;
    private String notifyTimeAt;
}
