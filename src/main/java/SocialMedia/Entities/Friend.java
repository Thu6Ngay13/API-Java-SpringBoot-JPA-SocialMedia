package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "Friend")
public class Friend implements Serializable{
	private static final long serialVersionUID = 3808802474750908577L;
	
	@EmbeddedId
	private FriendId friendId;
	
	@Column
	private LocalDateTime requestTimeAt;
	
	@Column
<<<<<<< HEAD
	private boolean isAccepted;
=======
	private boolean IsAccepted;
>>>>>>> f225d5c2f1e0c490c8cc523833d84f952092d1ac
}
