package ch.guggi.restful;

public class RatedApp {

	private Integer appId;
	private String appName;
	private double ratingAvg;
	private Integer numberOfRatings;
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public double getRatingAvg() {
		return ratingAvg;
	}
	public void setRatingAvg(double d) {
		this.ratingAvg = d;
	}
	public Integer getNumberOfRatings() {
		return numberOfRatings;
	}
	public void setNumberOfRatings(Integer numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}
	
}
