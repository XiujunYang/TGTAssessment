package com.tgt.example.httpentity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "post", "email","sms"})
public class RequestPreference {
	private String name;
	private String post;
	private String email;
	private String sms;

	public RequestPreference() {}

	public RequestPreference(String name, String post, String email, String sms) {
		super();
		this.name = name;
		this.post = post;
		this.email = email;
		this.sms = sms;
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
		return "RequestPreference [name=" + name + ", post=" + post + ", email=" + email + ", sms=" + sms + "]";
	}
}
