package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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
	private long postId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String text;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String mediaURL;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime postTimeAt;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private Set<Report> reports;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posterId")
	private Account posterAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modeId")
	private Mode mode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId")
	private SocialGroup group;
	
	@ManyToMany(mappedBy = "postShares", fetch = FetchType.LAZY)
	private Set<Account> sharerAccounts;
	
	@ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
	private Set<Account> accountLikes;
}
