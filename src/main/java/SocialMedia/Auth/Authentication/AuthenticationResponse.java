package SocialMedia.Auth.Authentication;

import SocialMedia.Enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	private String accessToken;
    private String refreshToken;
    private boolean error;
    private boolean success;
    private String message;
    private String fullName;
    private String username;
    private String email;
    private String avatarurl;
    private int isBanned;
    private Role role;
}
