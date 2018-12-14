package com.mondiamedia.tech.task.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mondiamedia.tech.task.entities.VideoEntity;
import com.mondiamedia.tech.task.repositories.VideoRepository;
import com.mondiamedia.tech.task.services.VideoService;
import com.mondiamedia.tech.task.shared.Utils;
import com.mondiamedia.tech.task.shared.dto.VideoDto;

@Service
public class VideoServiceImpl implements VideoService {
	
	@Autowired
	VideoRepository videoRepository;
	
	@Autowired
	Utils utils;

	@Override
	public VideoDto addVideo(String userId, VideoDto video) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		VideoEntity videoEntity = modelMapper.map(video, VideoEntity.class);
		
		videoEntity.setUserId(userId);
		
		videoEntity.setVideoId(utils.genrateRandonString(50));
		
		VideoEntity savedVideo = videoRepository.save(videoEntity);
		
		VideoDto videoDto = modelMapper.map(savedVideo, VideoDto.class);
		
		return videoDto;
	}

	@Override
	public VideoDto getVideoByVideoId(String videoId) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		VideoEntity videoEntity = videoRepository.findByVideoId(videoId);
		
		if(videoEntity == null) return null;
		
		VideoDto videoDto = modelMapper.map(videoEntity, VideoDto.class);
		
		return videoDto;
	}

	@Override
	public List<VideoDto> getVideosByUserId(String userId, int page, int limit) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videos = new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<VideoEntity> VideosPage = videoRepository.findByUserId(pageableRequest, userId);
		
		List<VideoEntity> videosEntity = VideosPage.getContent();
		
		for(VideoEntity videoEntity: videosEntity) {
			
			VideoDto videoDto = modelMapper.map(videoEntity, VideoDto.class);
			
			videos.add(videoDto);
		}
		
		return videos;
	}

	@Override
	public List<VideoDto> getVideosByCategory(String categoryName, int page, int limit) {

		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videos = new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<VideoEntity> VideosPage = videoRepository.findByCategory(pageableRequest, categoryName);
		
		List<VideoEntity> videosEntity = VideosPage.getContent();
		
		for(VideoEntity videoEntity: videosEntity) {
			
			VideoDto videoDto = modelMapper.map(videoEntity, VideoDto.class);
			
			videos.add(videoDto);
		}
		
		return videos;
	}

	@Override
	public VideoDto updateVideo(String videoId, String userId, VideoDto video) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		VideoEntity videoEntity =  videoRepository.findByUserIdAndVideoId(userId, videoId);
		
		if(videoEntity == null) return null;
		
		videoEntity.setCategory(video.getCategory());
		
		videoEntity.setTitle(video.getTitle());
		
		videoEntity.setDescription(video.getDescription());
		
		VideoEntity savedVideoEntity =  videoRepository.save(videoEntity);
		
		VideoDto videoDto = modelMapper.map(savedVideoEntity, VideoDto.class);
		
		return videoDto;
	}

	@Override
	public void deleteVideo(String userId, String videoId) {
		
		VideoEntity videoEntity =  videoRepository.findByUserIdAndVideoId(userId, videoId);
		
		if(videoEntity == null) throw new RuntimeException("404 video not found!");
		
		videoRepository.delete(videoEntity);
		
	}

	@Override
	public List<VideoDto> getAllVideos(int page, int limit) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videos = new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<VideoEntity> VideosPage = videoRepository.findAll(pageableRequest);
		
		List<VideoEntity> videosEntity = VideosPage.getContent();
		
		for(VideoEntity videoEntity: videosEntity) {
			
			VideoDto videoDto = modelMapper.map(videoEntity, VideoDto.class);
			
			videos.add(videoDto);
		}
		
		return videos;
	}

	@Override
	public boolean checkIfVideoExists(String userId, String videoId) {
		
		VideoEntity videoEntity =  videoRepository.findByUserIdAndVideoId(userId, videoId);
		
		if(videoEntity == null) return false;
		
		return true;
	}

}
