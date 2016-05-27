package ch.guggi.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

@SequenceGenerator(name="appID", initialValue=1, allocationSize=1)
@Table(name="App")
public class App {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="appID", nullable=false)
	private Integer appID;
	
	@Column(name="appName")
	private String appName;
	
//	@Column(name="appScore")
//	private Integer appScore;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="app")
	private Set<Rating> ratings;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="app")
	private Set<User> users;

	
	// Getter Setters
	
//	public Integer getAppScore() {
//		return appScore;
//	}
//	
//	public void setAppScore(Integer appScore) {
//		this.appScore = appScore;
//	}
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public Integer getAppID() {
		return appID;
	}
	public void setAppID(Integer appID) {
		this.appID = appID;
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
