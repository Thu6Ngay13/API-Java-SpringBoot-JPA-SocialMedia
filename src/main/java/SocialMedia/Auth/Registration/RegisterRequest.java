package SocialMedia.Auth.Registration;

import java.time.LocalDateTime;

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
    private LocalDateTime dateOfBirth;
    private String email;
    private String password;
}