package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor

@Entity
@Table
public class Comment implements Serializable{
	private static final long serialVersionUID = -7685804469201358300L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long commentId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String text;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String mediaURL;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime commentTimeAt;
	
	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;
}
