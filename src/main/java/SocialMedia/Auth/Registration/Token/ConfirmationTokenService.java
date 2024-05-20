package SocialMedia.Auth.Registration.Token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Account;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

	public Optional<ConfirmationToken> getTokenByUser(Account account) {
		return confirmationTokenRepository.findByAccount(account);
	}

	public void delete(ConfirmationToken value) {
		confirmationTokenRepository.delete(value);
	}
}