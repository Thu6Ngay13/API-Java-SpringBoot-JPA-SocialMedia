package SocialMedia.Auth.ResetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordResponse {
	private String message;
    private boolean error;
    private boolean success;
}
