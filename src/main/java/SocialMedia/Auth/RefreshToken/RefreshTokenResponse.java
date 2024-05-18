package SocialMedia.Auth.RefreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder	
public class RefreshTokenResponse {
	private String accessToken;
	private String refreshToken;
	private boolean error;
	private boolean success;
	private String message;
}
