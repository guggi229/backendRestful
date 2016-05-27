package ch.guggi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name="Rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RatingID", nullable=false)
	private Integer ratingID;
	
	@Column(name="RatingPosComment")
	private String ratingPosComment;
	
	@Column(name="RatingNegComment")
	private String ratingNegComment;
	
	@Column(name="RatingScore")
	private Integer ratingScore;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AppID", nullable = false)
	private App app;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UserID", nullable = false)
	private User user;

	// Standard Konstruktor
	public Rating(){
		
	}
	
	// getter Setter
	public App getApp() {
		return this.app;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public String getRatingPosComment() {
		return ratingPosComment;
	}

	public void setRatingPosComment(String ratingPosComment) {
		this.ratingPosComment = ratingPosComment;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRatingNegComment() {
		return ratingNegComment;
	}

	public void setRatingNegComment(String ratingNegComment) {
		this.ratingNegComment = ratingNegComment;
	}

	public Integer getRatingScore() {
		return ratingScore;
	}


	public void setRatingScore(Integer ratingScore) {
		this.ratingScore = ratingScore;
	}


	public Integer getRatingID() {
		return ratingID;
	}


	public void setRatingID(Integer ratingID) {
		this.ratingID = ratingID;
	}
	
}
