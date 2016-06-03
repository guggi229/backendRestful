package ch.guggi.restful;

public class RatedApp {

	private Integer userId;
	private String posComment;
	private String negComment;
	private float ratingValue;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public float getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(float ratingValue) {
		this.ratingValue = ratingValue;
	}
}
