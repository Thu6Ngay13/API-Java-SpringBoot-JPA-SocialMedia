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
public class Report implements Serializable{
	private static final long serialVersionUID = -7539622221136775969L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long reportId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String content;
	
	@Column
	private Boolean isHandled;
	
	@Column
	private LocalDateTime reportingTimeAt;
	
	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="reporterUsername")
	private Account reporterAccount;
}
