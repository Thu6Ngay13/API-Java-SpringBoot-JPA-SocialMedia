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
public class Conversation implements Serializable{
	private static final long serialVersionUID = 1332829846344615633L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long conversationId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String conversationName;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime creationTimeAt;
}
