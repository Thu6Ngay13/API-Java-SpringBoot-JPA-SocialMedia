package SocialMedia.Models;

import SocialMedia.Enums.TypeFriendEnum;
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
public class FriendModel {
	private TypeFriendEnum viewType;
    private String avatar;
    private String username;
    private String fullName;
    private String requestTimeAt;
}
