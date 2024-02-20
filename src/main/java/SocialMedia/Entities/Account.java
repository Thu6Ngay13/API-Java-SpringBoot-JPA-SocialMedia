package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Account implements Serializable{
	private static final long serialVersionUID = 3808802474750908577L;
	
	@Id
	@Column(columnDefinition = "varchar(50)")
	private String username;
	
	@Column(columnDefinition = "varchar(50)")
	private String password;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String fullname; 
	
	@Column(columnDefinition = "nvarchar(50)")
	private String gender;
	
	@Column
	private LocalDateTime dateOfBirth;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String avatarURL;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String email;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String phoneNumber;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String token;
	
	@Column
	private boolean enable;
	
	@Column
	private boolean isBanned;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "Account_AccountType",
			joinColumns = {@JoinColumn(name = "username")},
			inverseJoinColumns = {@JoinColumn(name = "typeId")})
	private Set<AccountType> accountTypes;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<Notification> notifications;
	
	@OneToMany(mappedBy = "posterAccount", fetch = FetchType.LAZY)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "sharerAccount", fetch = FetchType.LAZY)
	private List<Post> shares;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<Friend> friends;
	
	@ManyToMany
	@JoinTable(name = "Account_SocialGroup",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "groupId")})
	private Set<SocialGroup> socialGroups;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Account_Conversation",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "conversationId")})
	private Set<Conversation> conversations;
	
	@ManyToMany
	@JoinTable(name = "Account_Block_Account",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "blockUsername")})
	private Set<Account> BlockAccounts;
	
	
}