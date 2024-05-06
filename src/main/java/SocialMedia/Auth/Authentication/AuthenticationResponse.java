package SocialMedia.Auth.Authentication;

import java.time.LocalDateTime;

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
    private String location;
    private LocalDateTime dateOfBirth;
    private String description;
    private String gender;
}
