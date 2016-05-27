package ch.guggi.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateUser {
	private String userName;
	private Integer userID;
	private Integer appID;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getAppID() {
		return appID;
	}
	public void setAppID(Integer appID) {
		this.appID = appID;
	}
	

}
