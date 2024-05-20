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
public class SocialGroupModel {
	private long groupId;
	private String groupName;
	private String avatarURL;
	private String creationTimeAt;
	private long modeId;
	private String holderFullName;
	private String holderUsername;
	private String description;
}