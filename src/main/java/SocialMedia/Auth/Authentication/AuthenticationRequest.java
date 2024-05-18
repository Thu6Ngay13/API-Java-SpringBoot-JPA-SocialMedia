package SocialMedia.Auth.Authentication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
	private String emailOrUsername;
    private String password;
}
