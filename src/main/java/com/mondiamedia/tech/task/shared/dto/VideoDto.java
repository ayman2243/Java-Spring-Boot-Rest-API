package com.mondiamedia.tech.task.shared.dto;

import java.io.Serializable;

public class VideoDto implements Serializable {

	private static final long serialVersionUID = -4304094580120992462L;
	
	private long id;
	
	private String videoId;
	
	private String userId;
	
	private String category;
	
	private String title;
	
	private String description;
	
	private String videoDownloadUrl;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getVideoDownloadUrl() {
		return videoDownloadUrl;
	}
	public void setVideoDownloadUrl(String videoDownloadUrl) {
		this.videoDownloadUrl = videoDownloadUrl;
	}
	
}
