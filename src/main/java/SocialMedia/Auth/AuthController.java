package SocialMedia.Auth;

import java.util.Map;

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
}
