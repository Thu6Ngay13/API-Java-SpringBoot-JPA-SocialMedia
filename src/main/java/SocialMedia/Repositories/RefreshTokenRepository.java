package SocialMedia.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SocialMedia.Entities.Account;
import SocialMedia.Entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByAccount(Account account);

}
