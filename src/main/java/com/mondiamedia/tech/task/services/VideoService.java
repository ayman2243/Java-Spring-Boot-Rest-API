package com.mondiamedia.tech.task.services;

import java.util.List;

import com.mondiamedia.tech.task.shared.dto.VideoDto;

public interface VideoService {
	
	VideoDto addVideo(String userId, VideoDto video);
	
	VideoDto getVideoByVideoId(String videoId);
	
	List<VideoDto> getVideosByUserId(String userId, int page, int limit);
	
	List<VideoDto> getVideosByCategory(String categoryName, int page, int limit);
	
	VideoDto updateVideo(String videoId, String userId, VideoDto video);
	
	void deleteVideo(String userId, String videoId);
	
	List<VideoDto> getAllVideos(int page, int limit);
	
	boolean checkIfVideoExists(String userId, String videoId);
}
