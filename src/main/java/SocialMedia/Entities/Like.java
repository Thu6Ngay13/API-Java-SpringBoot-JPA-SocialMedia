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
@Table(name = "Account_like_Post")
public class Like implements Serializable{
	
	private static final long serialVersionUID = 3865344730191002728L;

	@EmbeddedId
	private LikeId id;
	
	@Column(name = "likeTimeAt")
	private LocalDateTime likeTimeAt;
}
