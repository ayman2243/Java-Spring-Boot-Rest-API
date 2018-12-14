package com.mondiamedia.tech.task.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="videos")
public class VideoEntity implements Serializable {

	private static final long serialVersionUID = -5907884704221036354L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false, length=255)
	private String videoId;
	
	@Column(nullable=false, length=255)
	private String userId;
	
	@Column(nullable=false, length=255)
	private String category;
	
	@Column(nullable=false, length=255)
	private String title;
	
	@Column(nullable=false, length=1000)
	private String description;
	
	@Column(nullable=false, length=255)
	private String videoDownloadUrl;
	
	public String getVideoDownloadUrl() {
		return videoDownloadUrl;
	}

	public void setVideoDownloadUrl(String videoDownloadUrl) {
		this.videoDownloadUrl = videoDownloadUrl;
	}
	
	@ManyToOne
	@JoinTable(name="users")
	@JoinColumn(name="user_id")
	private UserEntity user;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
