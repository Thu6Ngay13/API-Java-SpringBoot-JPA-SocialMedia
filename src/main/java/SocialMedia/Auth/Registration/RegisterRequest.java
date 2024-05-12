package SocialMedia.Auth.Registration;

import java.time.LocalDateTime;

import SocialMedia.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String fullname;
    private String dateOfBirth;
    private String email;
    private String password;
}