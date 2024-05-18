package SocialMedia.Entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RefreshToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long refreshTokenId;

    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;
}
