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
public class Report implements Serializable{
	private static final long serialVersionUID = -7539622221136775969L;

	@Id
	@Column
	private int reportId;
	
	@Column
	private String text;
	
	@Column
	private String isHandled;
	
	@Column
	private LocalDateTime reportingTimeAt;
}
