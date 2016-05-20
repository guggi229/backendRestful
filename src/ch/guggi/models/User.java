package ch.guggi.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/*
 * Model der User für das OO-Mapping
 * 
 * 
 */

@XmlRootElement
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
	
	@Column(name="AppID")
	private String appID;

	
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

	public String getUserOwnApp() {
		return appID;
	}

	public void setUserOwnApp(String userOwnApp) {
		this.appID = userOwnApp;
	}
	
}
