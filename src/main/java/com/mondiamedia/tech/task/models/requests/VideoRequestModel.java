package com.mondiamedia.tech.task.models.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VideoRequestModel {
	
	@NotNull(message="category is required!")
	@Size(min=2, max=255, message="category must be equal or greater than 2 characters and less than 255 characters!")
	private String category;
	
	@NotNull(message="title is required!")
	@Size(min=2, max=255, message="title must be equal or greater than 2 characters and less than 255 characters!")
	private String title;
	
	@NotNull(message="description is required!")
	@Size(min=2, max=1000, message="description must be equal or greater than 2 characters and less than 1000 characters!")
	private String description;
	
	private String videoDownloadUrl;

	public String getVideoDownloadUrl() {
		return videoDownloadUrl;
	}

	public void setVideoDownloadUrl(String videoDownloadUrl) {
		this.videoDownloadUrl = videoDownloadUrl;
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
