package SocialMedia.Auth;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SocialMedia.Auth.Authentication.AuthenticationRequest;
import SocialMedia.Auth.Authentication.AuthenticationResponse;
import SocialMedia.Auth.Email.EmailService;
import SocialMedia.Auth.Otp.OtpResponse;
import SocialMedia.Auth.RefreshToken.RefreshTokenResponse;
import SocialMedia.Auth.Registration.EmailValidator;
import SocialMedia.Auth.Registration.RegisterRequest;
import SocialMedia.Auth.Registration.RegisterResponse;
import SocialMedia.Auth.Registration.Token.ConfirmationToken;
import SocialMedia.Auth.Registration.Token.ConfirmationTokenService;
import SocialMedia.Auth.ResetPassword.ResetPasswordRequest;
import SocialMedia.Auth.ResetPassword.ResetPasswordResponse;
import SocialMedia.Entities.Account;
import SocialMedia.Entities.RefreshToken;
import SocialMedia.Enums.Role;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Response.Response;
import SocialMedia.Security.JWTService;
import SocialMedia.Services.IAccountService;
import SocialMedia.Services.RefreshTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RefreshTokenService refreshTokenService;

    private final EmailValidator emailValidator;

    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest request) {
        Boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            return RegisterResponse.builder().error(true).success(false).message("Email Is Invalid!").build();
        }
        Optional<Account> optAccount = repository.findByEmail(request.getEmail());
        if(optAccount.isEmpty()) {
        	LocalDateTime dateOfBirth = LocalDateTime.parse(request.getDateOfBirth());
        	System.out.println(dateOfBirth);
            var user = Account.builder()
                    .fullname(request.getFullname())
                    .username(request.getUsername())
                    .dateOfBirth(dateOfBirth)
                    .email(request.getEmail())
                    .avatarURL("https://drive.google.com/uc?export=view&id=1LZuoz5KlfRIOJiolzkcGDva0GaCN_NCl")
                    .isSingle(true)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .enable(false)
                    .role(Role.USER)
                    .build();
            repository.save(user);

            String token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user

            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);     
            emailService.send(request.getEmail(), buildEmail(request.getFullname(), token));
            return RegisterResponse.builder()
                    .message("Please Check Email To See OTP!")
                    .error(false)
                    .success(true)
                    .build();
        }
        else {
        	return RegisterResponse.builder()
                    .message("Email Already Taken!")
                    .error(false)
                    .success(true)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailOrUsername(),
                        request.getPassword()
                )
        );
        Optional<Account> opt = repository.findByEmailOrUsername(request.getEmailOrUsername());
        if(opt.isEmpty()) {
            return AuthenticationResponse.builder().error(true).message("Email or Password wrong!").success(false).build();
        }
        Account account = opt.get();
        if(!account.isEnable()) {
            return AuthenticationResponse.builder().error(true).message("Account Not Confirm!").success(false).build();
        }
        var jwtToken = jwtService.generateAccessToken(account);
        var refreshToken = refreshTokenService.createRefreshToken(account.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken.getToken())
                .fullName(account.getFullname())
                .username(account.getUsername())
                .email(account.getEmail())
                .avatarurl(account.getAvatarURL())
                .role(account.getRole())
                .avatarurl(account.getAvatarURL())
                .isBanned(account.isBanned() == true ? 1 : 0)
                .error(false)
                .success(true)
                .message("Successfully!")
                .build();
    }

    public OtpResponse confirmToken(String token) {
        Optional<ConfirmationToken> otpConfirmationToken = confirmationTokenService.getToken(token);
        if(otpConfirmationToken.isPresent()) {
            ConfirmationToken confirmationToken = otpConfirmationToken.get();
            if(confirmationToken.getConfirmedAt() != null) {
                return OtpResponse.builder()
                        .message("Email Already Confirmed")
                        .error(true)
                        .success(false)
                        .build();
            }
            LocalDateTime expiredAt = confirmationToken.getExpiredAt();
            if(expiredAt.isBefore(LocalDateTime.now())) {
                return OtpResponse.builder()
                        .message("Token Expired")
                        .error(true)
                        .success(false)
                        .build();
            }
            confirmationTokenService.setConfirmedAt(token);
            accountService.enableUser(confirmationToken.getAccount().getEmail());
            return OtpResponse.builder().message("Confirmed!").error(false).success(true).build();
        }
        return OtpResponse.builder().message("Token Not Valid!").error(true).success(false).build();
    }
    
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    private String buildEmail(String fullname, String token) {
        return "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        background-color: #f4f4f4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "      .container {\n" +
                "        max-width: 600px;\n" +
                "        margin: 50px auto;\n" +
                "        padding: 20px;\n" +
                "        background-color: #fff;\n" +
                "        border-radius: 10px;\n" +
                "        box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
                "      }\n" +
                "      h1 {\n" +
                "        color: #333;\n" +
                "      }\n" +
                "      h2 {\n" +
                "        color: #555;\n" +
                "      }\n" +
                "      span {\n" +
                "        color: #ff0000;\n" +
                "        font-weight: bold;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <h1>Xin Chào, " + fullname + "!</h1>\n" +
                "      <h2>Mã OTP để xác nhận tài khoản của bạn là: <span>" + token + "</span></h2>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

    public Object refreshToken(String token) {
        Optional<RefreshToken> opt = refreshTokenService.findByToken(token);
        if(opt.isPresent()) {
        	try {
        		RefreshToken refreshToken = refreshTokenService.verifyExpiration(opt.get());
        		String accessToken = jwtService.generateAccessToken(refreshToken.getAccount());
                return RefreshTokenResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(token)
                        .error(false)
                        .success(true)
                        .message("Refresh Token Successfully!")
                        .build();
        	} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
        	throw new RuntimeException("Refresh Token not in database!");
        }
        return null;
    }

	public Object sendEmail(String email) {
		Optional<Account> optAccount = accountService.findByEmail(email);
        if(optAccount.isPresent()) {
            Account account = optAccount.get();
            String token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    account
            );
            Optional<ConfirmationToken> optConfirmationToken = confirmationTokenService.getTokenByUser(account);
            optConfirmationToken.ifPresent(value -> confirmationTokenService.delete(value));
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            emailService.send(email, buildEmail(account.getFullname(), token));
            return Response.builder()
            		.message("Please Check Email To See OTP!")
                    .success(true)
                    .result(null)
                    .build();
        }
        return RegisterResponse.builder()
                .message("Email Not Register!")
                .error(true)
                .success(false)
                .build();
	}
	public Optional<Account> findByEmail(String email) {
		return accountService.findByEmail(email);
	}
	public ResetPasswordResponse resetPassword(ResetPasswordRequest req) {
        Optional<ConfirmationToken> otpConfirmationToken = confirmationTokenService.getToken(req.getToken());
        if(otpConfirmationToken.isPresent()) {
            ConfirmationToken confirmationToken = otpConfirmationToken.get();
            if(confirmationToken.getConfirmedAt() != null) {
                return ResetPasswordResponse.builder()
                        .message("Email Already Confirmed")
                        .error(true)
                        .success(false)
                        .build();
            }
            LocalDateTime expiredAt = confirmationToken.getExpiredAt();
            if(expiredAt.isBefore(LocalDateTime.now())) {
                return ResetPasswordResponse.builder()
                        .message("Token Expired")
                        .error(true)
                        .success(false)
                        .build();
            }
            confirmationTokenService.setConfirmedAt(req.getToken());
            accountService.updatePassword(passwordEncoder.encode(req.getNewPassword()), req.getEmail());
            return ResetPasswordResponse.builder().message("Reset password success").error(false).success(true).build();
        }
        return ResetPasswordResponse.builder().message("Token Not Valid!").error(true).success(false).build();
    }
}
