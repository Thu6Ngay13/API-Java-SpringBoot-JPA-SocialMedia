package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class Notification implements Serializable{
	private static final long serialVersionUID = -4421076874655461723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int notifyId;
	
	@Column
	private String content;
	
	@Column
	private boolean isSeen;
	
	@Column
	private LocalDateTime notificationTimeAt;
}
