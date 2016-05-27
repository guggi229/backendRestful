package ch.guggi.restful;

public class RatingRequest {
	private Integer userId;
	private Integer appId;
	private Integer rating;
	private String posComment;
	private String negComment;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getPosComment() {
		return posComment;
	}
	public void setPosComment(String posComment) {
		this.posComment = posComment;
	}
	public String getNegComment() {
		return negComment;
	}
	public void setNegComment(String negComment) {
		this.negComment = negComment;
	}
}
