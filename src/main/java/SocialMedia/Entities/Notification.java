package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table
public class Notification implements Serializable{
	private static final long serialVersionUID = -4421076874655461723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long notifyId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String text;
	
	@Column
	private boolean isSeen;
	
	@Column
	private LocalDateTime notificationTimeAt;

	@ManyToMany(mappedBy = "notificationReceipts", fetch = FetchType.LAZY)
	private Set<Account> accountReceipts;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="username")
	private Account accountCreate;
}
