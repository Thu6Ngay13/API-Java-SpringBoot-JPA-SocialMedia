package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

	@Column
	@ElementCollection
	@JoinTable(name = "accountImages", joinColumns = @JoinColumn(name = "username"))
    private List<String> images;
}