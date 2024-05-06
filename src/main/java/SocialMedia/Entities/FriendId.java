package SocialMedia.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FriendId implements Serializable{
	private static final long serialVersionUID = 3808802474750908577L;
	
	@Column(columnDefinition = "varchar(50)")
	private String usernameYou;
	
	@Column(columnDefinition = "varchar(50)")
	private String usernameFriend;
}
