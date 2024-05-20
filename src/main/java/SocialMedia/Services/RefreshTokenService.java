package SocialMedia.Services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.RefreshToken;
import SocialMedia.Repositories.AccountRepository;
import SocialMedia.Repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {
	 private RefreshTokenRepository refreshTokenRepository;
	    private AccountRepository accountRepository;

	    public RefreshToken createRefreshToken(String email) {
	        Optional<Account> optAccount = accountRepository.findByEmail(email);
	        if(optAccount.isPresent()) {
	            Account account = optAccount.get();
	            Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByAccount(account);
	            optRefreshToken.ifPresent(token -> refreshTokenRepository.delete(token));
	            RefreshToken refreshToken = RefreshToken.builder()
	                    .account(accountRepository.findByEmail(email).get())
	                    .token(UUID.randomUUID().toString())
	                    .expiryDate(Instant.now().plusMillis(600000))
	                    .build();
	            return refreshTokenRepository.save(refreshToken);
	        }
	        return null;
	    }

	    public Optional<RefreshToken> findByToken( String token) {
	        return refreshTokenRepository.findByToken(token);
	    }

	    public RefreshToken verifyExpiration(RefreshToken token) {
	        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
	            refreshTokenRepository.delete(token);
	            throw new RuntimeException(token.getToken() + "Refresh Token was expired. Please make a new signin request!");
	        }
	        return token;
	    }
}
