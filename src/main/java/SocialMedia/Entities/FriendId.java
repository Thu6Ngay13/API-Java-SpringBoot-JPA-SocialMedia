package SocialMedia.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Embeddable
public class FriendId implements Serializable{
	private static final long serialVersionUID = 3808802474750908577L;
	
	@Column(columnDefinition = "varchar(50)")
	private String usernameYou;
	
	@Column(columnDefinition = "varchar(50)")
	private String usernameFriend;

	public FriendId(String usernameYou, String usernameFriend) {
		super();
		this.usernameYou = usernameYou;
		this.usernameFriend = usernameFriend;
	}

}
