package com.tgt.example.httpentity;

public class ResponsePerference {
	private String userId;
	private String name;
	private String post;
	private String email;
	private String sms;

	public ResponsePerference(String userId) {
		this.userId = userId;
	}

	public ResponsePerference(String userId, String name, String post, String email, String sms) {
		this.userId = userId;
		this.name = name;
		this.post = post;
		this.email = email;
		this.sms = sms;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	@Override
	public String toString() {
		return "ResponseBaseEntity [userId=" + userId + ", name=" + name + ", post=" + post + ", email=" + email
				+ ", sms=" + sms + "]";
	}
}