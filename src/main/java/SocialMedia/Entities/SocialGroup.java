package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor

@Entity
@Table
public class SocialGroup implements Serializable{
	private static final long serialVersionUID = -2919537406136480729L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long groupId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String groupName;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String avatarURL;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime creationTimeAt;
}
