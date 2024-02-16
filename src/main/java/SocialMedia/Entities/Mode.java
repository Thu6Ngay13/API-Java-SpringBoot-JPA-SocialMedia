package SocialMedia.Entities;

import java.io.Serializable;

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
	private int modeId;
	
	@Column
	private String modeType;
}
