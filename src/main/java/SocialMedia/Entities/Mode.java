package SocialMedia.Entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor

@Entity
@Table
public class Mode implements Serializable{
	private static final long serialVersionUID = 6563500481127907238L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long modeId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String modeType;
	
	@OneToMany(mappedBy = "mode", fetch = FetchType.LAZY)
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "mode", fetch = FetchType.LAZY)
	private Set<Share> shares;
	
	@OneToMany(mappedBy = "mode", fetch = FetchType.LAZY)
	private Set<SocialGroup> groups;
}
