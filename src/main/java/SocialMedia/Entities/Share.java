package SocialMedia.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "Account_share_Post")
public class Share implements Serializable {
	
	private static final long serialVersionUID = 5755750293160330975L;

	@EmbeddedId
	private ShareId shareId;

	@Column
	private LocalDateTime shareTimeAt;

	@Column
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modeId")
	private Mode mode;

}
