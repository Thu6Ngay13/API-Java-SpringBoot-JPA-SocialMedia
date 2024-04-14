package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	
	@Column(columnDefinition = "nvarchar(100)")
    private String description;
    
	@Column(columnDefinition = "nvarchar(100)")
	private String company;
	
	@Column(columnDefinition = "nvarchar(100)")
    private String location;
	
	@Column
    private boolean isSingle;
	
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
	
	@OneToMany(mappedBy = "accountCreate", fetch = FetchType.LAZY)
	private Set<Notification> notificationCreates;
	
	@OneToMany(mappedBy = "posterAccount", fetch = FetchType.LAZY)
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "sharerAccount", fetch = FetchType.LAZY)
	private Set<Post> shares;
	
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<Comment> comments;

	@ManyToMany
	@JoinTable(name = "Friend",
		joinColumns = {@JoinColumn(name = "petitionerId") },
		inverseJoinColumns = {@JoinColumn(name = "requestedPersonId")})
	private Set<Account> requestedPersonAccounts;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Notification_Receipt",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "notifyId")})
	private Set<Notification> notificationReceipts;
	
	@ManyToMany
	@JoinTable(name = "Account_SocialGroup",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "groupId")})
	private Set<SocialGroup> joinedSocialGroups;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Account_Conversation",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "conversationId")})
	private Set<Conversation> conversations;
	
	@ManyToMany
	@JoinTable(name = "Account_Block_Account",
		joinColumns = {@JoinColumn(name = "username") },
		inverseJoinColumns = {@JoinColumn(name = "blockedUsername")})
	private Set<Account> blockedAccounts;
	
	@OneToMany(mappedBy = "holderAccount", fetch = FetchType.LAZY)
	private Set<SocialGroup> holdedSocialGroups;
	
	@OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY)
	private Set<Message> messages;
}