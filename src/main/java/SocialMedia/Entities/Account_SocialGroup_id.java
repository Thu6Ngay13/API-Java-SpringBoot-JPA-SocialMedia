package SocialMedia.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Account_SocialGroup_id implements Serializable{

	private static final long serialVersionUID = 2523649523659753478L;
	@Column
	private long groupId;
	
	@Column(columnDefinition = "varchar(50)")
	private String username;
}
