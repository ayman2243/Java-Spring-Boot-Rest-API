package com.mondiamedia.tech.task.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mondiamedia.tech.task.models.requests.VideoRequestModel;
import com.mondiamedia.tech.task.models.responses.OperationStatusModel;
import com.mondiamedia.tech.task.models.responses.RequestOperationNames;
import com.mondiamedia.tech.task.models.responses.RequestOperationStatus;
import com.mondiamedia.tech.task.models.responses.VideoResponseModel;
import com.mondiamedia.tech.task.services.UserService;
import com.mondiamedia.tech.task.services.VideoService;
import com.mondiamedia.tech.task.shared.Utils;
import com.mondiamedia.tech.task.shared.dto.UserDto;
import com.mondiamedia.tech.task.shared.dto.VideoDto;

@RestController
@RequestMapping("videos")
public class VideoController {
	
	private static String UPLOAD_DIR = "uploads";
	private static long MAX_UPLOAD_SIZE = 500000000;
	private static String ALLOWED_TYPE = "video/mp4";
	
	@Autowired
	UserService userService;
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	Utils utils;
	
	@GetMapping
	public ResponseEntity<List<VideoResponseModel>> getAllVideos(
								@RequestParam(value="page", defaultValue="0") int page,
								@RequestParam(value="limit", defaultValue="25") int limit) {
		List<VideoResponseModel> videos = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videosDto = videoService.getAllVideos(page, limit);
		
		for(VideoDto videoDto: videosDto ) {
			
			VideoResponseModel videoResponseModel = modelMapper.map(videoDto, VideoResponseModel.class);
			
			videos.add(videoResponseModel);
		}
		
		return ResponseEntity.ok().body(videos);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<VideoResponseModel> videosByVideoId(@PathVariable String id) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		VideoDto videoDto = videoService.getVideoByVideoId(id);
		
		if(videoDto == null) return ResponseEntity.notFound().build();

		VideoResponseModel video = modelMapper.map(videoDto, VideoResponseModel.class);
		
		return ResponseEntity.ok().body(video);
	}
	
	@GetMapping(path="/channel/{id}")
	public  ResponseEntity<List<VideoResponseModel>> videosByUserId(
						@PathVariable String id, 
						@RequestParam(value="page", defaultValue="0") int page,
						@RequestParam(value="limit", defaultValue="25") int limit) {
		
		List<VideoResponseModel> videos = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videosDto = videoService.getVideosByUserId(id, page, limit);
		
		for(VideoDto videoDto: videosDto ) {
			
			VideoResponseModel videoResponseModel = modelMapper.map(videoDto, VideoResponseModel.class);
			
			videos.add(videoResponseModel);
		}
		
		return ResponseEntity.ok().body(videos);
	}
	
	@GetMapping(path="/category/{category}")
	public  ResponseEntity<List<VideoResponseModel>> getVideosByCategory(
						@PathVariable String category,
						@RequestParam(value="page", defaultValue="0") int page,
						@RequestParam(value="limit", defaultValue="25") int limit) {
		
		List<VideoResponseModel> videos = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<VideoDto> videosDto = videoService.getVideosByCategory(category, page, limit);
		
		for(VideoDto videoDto: videosDto ) {
			
			VideoResponseModel videoResponseModel = modelMapper.map(videoDto, VideoResponseModel.class);
			
			videos.add(videoResponseModel);
		}
		
		return ResponseEntity.ok().body(videos);
	}
	
	@PostMapping( 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> addVideo( 
								@RequestParam(value="video", required = true) MultipartFile videoFile, 
								@RequestParam(value="category", required = true) String category,
								@RequestParam(value="title", required = true) String title,
								@RequestParam(value="description", required = true) String description,
								Authentication auth,
								HttpServletRequest req) throws MalformedURLException {
			
		String email = auth.getName();
		UserDto userDto2 = userService.getUser(email);
		
		String videoFileName = utils.uniqeVideoName()+".mp4";
		String videoFileType = videoFile.getContentType().toString();
		long videoFileSize = videoFile.getSize();
		
		if(videoFileSize > MAX_UPLOAD_SIZE || !videoFileType.contains(ALLOWED_TYPE)) {
			return ResponseEntity.badRequest()
						.body("Video size must be less than 50MB and video type must be MP4.");
		}
		
		String path = req.getServletContext().getResource("/").getPath()
					  + UPLOAD_DIR + File.separator + videoFileName;
		
		if(userDto2 == null || userDto2.getEmail() == null) return ResponseEntity.status(401).body("401 unauthorized");
		
		try {
			saveVideo(videoFile.getInputStream(), path);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		ModelMapper modelMapper = new ModelMapper();
		
		VideoDto videoDto = new VideoDto();
		videoDto.setCategory(category);
		videoDto.setTitle(title);
		videoDto.setDescription(description);
		videoDto.setVideoDownloadUrl(videoFileName);
				
		VideoDto savedVideo = videoService.addVideo(userDto2.getUserId(), videoDto);
		VideoResponseModel videoResponseModel = modelMapper.map(savedVideo, VideoResponseModel.class);
		
		return ResponseEntity.ok().body(videoResponseModel);
	}
	
	private void saveVideo(InputStream inputStream, String path) {
		try {
			OutputStream outputStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];	
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		}catch(Exception e) {
			e.getMessage();
		}
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<VideoResponseModel> editVideo(@PathVariable String id, @RequestBody VideoRequestModel video, Authentication auth) {
		
		String email = auth.getName();
		UserDto userDto2 = userService.getUser(email);
		
		ModelMapper modelMapper = new ModelMapper();
		VideoDto videoDto = modelMapper.map(video, VideoDto.class);
		
		VideoDto updatedVideo = videoService.updateVideo(id, userDto2.getUserId(), videoDto);
		
		if(updatedVideo == null) return ResponseEntity.notFound().build();
		
		VideoResponseModel videoResponseModel = modelMapper.map(updatedVideo, VideoResponseModel.class);
		
		return ResponseEntity.ok().body(videoResponseModel);
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<OperationStatusModel> deleteVideo(
			@PathVariable String id, 
			Authentication auth,
			HttpServletRequest req) throws MalformedURLException {
		
		String email = auth.getName();
		UserDto userDto2 = userService.getUser(email);
		
		OperationStatusModel result = new OperationStatusModel();
		
		result.setOperationName(RequestOperationNames.DELETE.name());
		
		if(!videoService.checkIfVideoExists(userDto2.getUserId(), id)) return ResponseEntity.notFound().build();
		
		VideoDto videoDto = videoService.getVideoByVideoId(id);
		
		File file=new File(req.getServletContext().getResource("/").getPath()+UPLOAD_DIR+File.separator+videoDto.getVideoDownloadUrl());
		if(file.exists()){
			file.delete();
		}
		
		videoService.deleteVideo(userDto2.getUserId(), id);
	
		result.setOperationResult(RequestOperationStatus.OK.name());

		return ResponseEntity.ok().body(result);
	}
}
