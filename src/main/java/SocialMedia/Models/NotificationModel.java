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
public class NotificationModel {
	private String avatar;
	private String username;
    private String fullName;
    private String text;
    private Boolean isSeen;
    private String notifyTimeAt;
}
