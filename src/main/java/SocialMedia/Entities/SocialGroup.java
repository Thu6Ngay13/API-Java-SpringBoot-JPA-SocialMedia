package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String avatarURL;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime creationTimeAt;
	
	
	@Column(columnDefinition = "nvarchar(500)")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "modeId")
	private Mode mode;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<Post> posts;
	
	@ManyToMany(mappedBy = "joinedSocialGroups")
	private Set<Account> joinedAccounts;
	
	@ManyToOne
	@JoinColumn(name = "holderUsername")
	private Account holderAccount;
}
