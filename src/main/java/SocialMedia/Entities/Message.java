package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Message implements Serializable{
	private static final long serialVersionUID = -8932391282476049113L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long messageId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String text;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String mediaURL;
	
	@Column
	private boolean isSeen;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime messageSendingAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="conversationId")
	private Conversation conversation;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="senderUsername")
	private Account senderAccount;
}
