package SocialMedia.Auth.Registration.Token;

import com.fasterxml.jackson.annotation.JsonBackReference;

import SocialMedia.Entities.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;

    @Column(name = "token", columnDefinition = "varchar(1000)")
    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Account account) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.account = account;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;
}