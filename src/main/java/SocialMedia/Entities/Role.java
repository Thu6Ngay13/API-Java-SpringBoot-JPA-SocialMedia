package SocialMedia.Entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor

@Entity
@Table
public class Role implements Serializable{
	
	private static final long serialVersionUID = -2654028452547691602L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long roleId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String roleName;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<Account> accounts;
}
