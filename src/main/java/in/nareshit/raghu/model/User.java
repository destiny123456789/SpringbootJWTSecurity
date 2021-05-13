package in.nareshit.raghu.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;


@Data
@Entity
@Table(name="usertab")
public class User {
	
	@Id
	@GeneratedValue
	@Column
	private Integer id;
	@Column
	private String username;
	@Column
	private String name;
	@Column
	private String password;
	@Column
	private String address;
	@Column
	private String pincode;
	@Column
	private String city;
	@Column
	private String email;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date joindate;
	
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dob;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name ="rolestab",
	                 joinColumns = @JoinColumn(name="id")
	                 
	)
	@Column(name="role")
	private Set<String> roles;
	
	/*
	 * @ElementCollection private List<String> roles;
	 */

}
