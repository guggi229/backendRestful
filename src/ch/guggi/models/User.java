package ch.guggi.models;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/*
 * Model der User für das OO-Mapping
 * 
 * 
 */

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Entity
@SequenceGenerator(name="UserID", initialValue=1, allocationSize=1)
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserID", nullable=false)
	private Integer userID;
	
	@Column(name="UserName")
	private String userName;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user")
	private Set<Rating> ratings;
	
	/*
	 * Getter / Setter
	 * 
	 * 
	 */
	
	public Integer getUserID() {
		return userID;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Set<Rating> getRatings() {
		return this.ratings;
	}
	
	public void setRating(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
}
