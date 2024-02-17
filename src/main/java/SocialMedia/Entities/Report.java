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
public class Report implements Serializable{
	private static final long serialVersionUID = -7539622221136775969L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int reportId;
	
	@Column(columnDefinition = "nvarchar(300)")
	private String text;
	
	@Column
	private Boolean isHandled;
	
	@Column
	private LocalDateTime reportingTimeAt;
}
