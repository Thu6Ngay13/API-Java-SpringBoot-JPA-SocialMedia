package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class Post implements Serializable{
	private static final long serialVersionUID = -1705120247964710762L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int postId;
	
	@Column(name = "Content", columnDefinition = "nvarchar(MAX)")
	private String content;
	
	@Column(name = "Media", columnDefinition = "varchar(2000)")
	private String media;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime postTimeAt;
}
