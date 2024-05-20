package SocialMedia.Entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SocialMedia.Auth.Registration.Token.ConfirmationToken;
import SocialMedia.Enums.Role;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Account implements UserDetails {

	private static final long serialVersionUID = -2714773135927699660L;

	@Id
	@Column(columnDefinition = "varchar(50)")
	private String username;

	@Column(columnDefinition = "varchar(1000)")
	private String password;

	@Column(columnDefinition = "nvarchar(50)")
	private String fullname;

	@Column(columnDefinition = "nvarchar(50)")
	private String gender;

	@Column
	private LocalDateTime dateOfBirth;

	@Column(columnDefinition = "nvarchar(1000)")
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

	@Column
	private boolean enable;

	@Column
	private boolean isBanned;

	@OneToMany(mappedBy = "accountCreate", fetch = FetchType.LAZY)
	private Set<Notification> notificationCreates;

	@OneToMany(mappedBy = "posterAccount", fetch = FetchType.LAZY)
	private Set<Post> posts;

	@OneToMany(mappedBy = "commenterAccount", fetch = FetchType.LAZY)
	private Set<Comment> comments;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Friend", joinColumns = { @JoinColumn(name = "usernameYou") }, inverseJoinColumns = {
			@JoinColumn(name = "usernameFriend") })
	private Set<Account> friends;

	@Enumerated(EnumType.STRING)
	@Basic(fetch = FetchType.LAZY)
	private Role role;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_SocialGroup", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "groupId") })
	private Set<SocialGroup> joinedSocialGroups;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_Conversation", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "conversationId") })
	private Set<Conversation> conversations;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_share_Post", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "postId") })
	private Set<Post> postShares;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_receipt_Notification", joinColumns = {
			@JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "notifyId") })
	private Set<Notification> notificationReceipts;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_block_Account", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "blockedUsername") })
	private Set<Account> blockedAccounts;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Account_like_Post", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = {
			@JoinColumn(name = "postId") })
	private Set<Post> likedPosts;

	@OneToMany(mappedBy = "holderAccount", fetch = FetchType.LAZY)
	private Set<SocialGroup> holdedSocialGroups;

	@OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY)
	private Set<Message> sendMessages;
	
	@OneToMany(mappedBy = "reporterAccount", fetch = FetchType.LAZY)
	private Set<Report> doReports;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private Set<ConfirmationToken> confirmationTokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enable;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

}