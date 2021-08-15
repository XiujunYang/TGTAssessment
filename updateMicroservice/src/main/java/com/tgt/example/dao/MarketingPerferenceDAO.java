package com.tgt.example.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "perference", schema="public")
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class MarketingPerferenceDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @Column(name = "user_id") private String userId;
	private String name; // customer's title or full name, could be using in pattern in following each perference.
	private String post;
	private String email;
	private String sms;

	@CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
	@UpdateTimestamp @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	
	public MarketingPerferenceDAO() {}
	
	public MarketingPerferenceDAO(String userId, String name, String post, String email, String sms, boolean deleted) {
		this.userId = userId;
		this.name = name;
		this.post = post;
		this.email = email;
		this.sms = sms;
		this.deleted = deleted;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "MarketingPerferenceDAO [userId=" + userId + ", name=" + name + ", post=" + post + ", email=" + email
				+ ", sms=" + sms + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleted=" + deleted
				+ "]";
	}
}