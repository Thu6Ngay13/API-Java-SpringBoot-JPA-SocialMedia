package SocialMedia.Models;

import SocialMedia.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountModel {

	private String username;
	private String fullname; 
	private String gender;
	private String avatarURL;
	private String email;
	private String phoneNumber;
	private String description;
	private String company;
	private String location;
	private boolean isSingle;
	private Role role;
}
