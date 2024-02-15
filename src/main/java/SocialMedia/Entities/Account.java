package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Account implements Serializable{
	private static final long serialVersionUID = 3808802474750908577L;
	
	@Id
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String fullname; 
	
	@Column
	private String gender;
	
	@Column
	private LocalDateTime dateOfBirth;
	
	@Column
	private String avatarURL;
	
	@Column
	private String email;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String token;
	
	@Column
	private boolean enable;
	
	@Column
	private boolean isBanned;

}