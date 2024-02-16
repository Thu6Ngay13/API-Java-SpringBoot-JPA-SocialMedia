package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
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
	private Integer groupId;
	
	@Column(name = "GroupName", columnDefinition = "nvarchar(2000)")
	private String groupName;
	
	@Column
	private String groupAvatar;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime creationTimeAt;
	
	@Column
	@ElementCollection
	@JoinTable(name = "groupImages", joinColumns = @JoinColumn(name = "group_id"))
    private List<String> images;
}
