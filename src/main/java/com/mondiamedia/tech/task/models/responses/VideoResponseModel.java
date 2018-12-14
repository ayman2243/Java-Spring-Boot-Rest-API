package com.mondiamedia.tech.task.models.responses;

public class VideoResponseModel {

	private String userId;
	
	private String videoId;
	
	private String category;
	
	private String title;
	
	private String description;
	
	private String videoDownloadUrl;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
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

	public String getVideoDownloadUrl() {
		return videoDownloadUrl;
	}

	public void setVideoDownloadUrl(String videoDownloadUrl) {
		this.videoDownloadUrl = videoDownloadUrl;
	}
	
	
	
	
}
