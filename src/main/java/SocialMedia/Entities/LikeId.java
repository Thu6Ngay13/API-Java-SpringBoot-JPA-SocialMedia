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
public class LikeId implements Serializable{

	private static final long serialVersionUID = 1582454104193328748L;

	@Column
	private long postId;
	
	@Column(columnDefinition = "varchar(50)")
	private String username;
}
