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
	private int commentId;
	
	@Column(name = "Content", columnDefinition = "nvarchar(MAX)")
	private String content;
	
	@Column(name = "Image", columnDefinition = "varchar(2000)")
	private String image;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime commentTimeAt;
}
