package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Message implements Serializable{
	private static final long serialVersionUID = -8932391282476049113L;
	
	@Id
	@Column
	private int messageId;
	
	@Column
	private String text;
	
	@Column
	private String imageURL;
	
	@Column
	private boolean isSeen;
	
	@Column
	private boolean isDeleted;
	
	@Column
	private LocalDateTime messageSendingAt;
}
