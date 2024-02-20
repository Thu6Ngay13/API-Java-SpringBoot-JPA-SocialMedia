package SocialMedia.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private boolean IsAccepted;
	
	@ManyToOne
	@JoinColumn(name="username")
	private Account account;
}
