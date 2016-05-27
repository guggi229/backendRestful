package ch.guggi.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/*
 * Model der App für das OO-Mapping
 * 
 * 
 */

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Entity
@SequenceGenerator(name="appId", initialValue=1, allocationSize=1)
@Table(name="App")
public class App {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="appId", nullable=false)
	private Integer appId;
	
	@Column(name="appName")
	private String appName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="app")
	private Set<Rating> ratings;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="app")
	private Set<User> users;

	@Transient
	private float ratingAVG;
	
	@Transient
	private Integer numberOfRatings;
	
	
	// Getter Setters
	
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	
	public Set<Rating> getRatings() {
		return this.ratings;
	}
	
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
	// Hibernate needs a default constructor
	public App(){
		
	}
	
/*
 * Vregleicht 2 Apps mit der Punktzahl
 * Standard comparableTo.......
 * 
 */
//	@Override
//	public int compareTo(App o) {
//		if (o.getAppScore()== null || this.getAppScore()==null) return -1;
//		return this.appScore.compareTo(o.appScore);
//	}
	
}
