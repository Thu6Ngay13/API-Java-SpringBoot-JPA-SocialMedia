package SocialMedia.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "Account_SocialGroup")
public class Account_SocialGroup implements Serializable{

	private static final long serialVersionUID = 1548184746034474471L;

	@EmbeddedId
	private Account_SocialGroup_id id;
	
	@Column(name = "isAccepted")
	private boolean isAccepted;
}
