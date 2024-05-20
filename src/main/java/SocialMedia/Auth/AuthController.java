package SocialMedia.Auth;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SocialMedia.Auth.Authentication.AuthenticationRequest;
import SocialMedia.Auth.RefreshToken.RefreshTokenRequest;
import SocialMedia.Auth.Registration.RegisterRequest;
import SocialMedia.Auth.ResetPassword.ResetPasswordRequest;
import SocialMedia.Entities.Account;
import SocialMedia.Models.AccountModel;
import SocialMedia.Response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(service.confirmToken(token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    	return ResponseEntity.ok(service.refreshToken(refreshTokenRequest.getToken()));
    }
    
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> reqBody) {
        String email = reqBody.get("email");
        return ResponseEntity.ok(service.sendEmail(email));
    }
    
    @PostMapping("/find-account")
    public ResponseEntity<?> findAccount(@RequestParam String email){
    	System.out.println(email);
    	Optional<Account> optionalAccount = service.findByEmail(email);
    	Account account = optionalAccount.orElse(null); 
    	AccountModel model = new AccountModel();
    	model.setAvatarURL(account.getAvatarURL());
    	model.setUsername(account.getUsername());
    	model.setFullname(account.getFullname());
    	model.setEmail(account.getEmail());
    	model.setRole(account.getRole());
    	return new ResponseEntity<Response>(
				new Response(true, "Thành công", model), 
				HttpStatus.OK
		);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(service.resetPassword(request));
    }
}
