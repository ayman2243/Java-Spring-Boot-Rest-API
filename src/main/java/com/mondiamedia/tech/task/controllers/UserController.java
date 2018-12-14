/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


import com.mondiamedia.tech.task.models.requests.UserRequestModel;
import com.mondiamedia.tech.task.models.responses.OperationStatusModel;
import com.mondiamedia.tech.task.models.responses.RequestOperationNames;
import com.mondiamedia.tech.task.models.responses.RequestOperationStatus;
import com.mondiamedia.tech.task.models.responses.UserResponseModel;
import com.mondiamedia.tech.task.services.UserService;
import com.mondiamedia.tech.task.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/me")
	public ResponseEntity<UserResponseModel> me(Authentication auth) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		String email = auth.getName();
		
		UserDto userDto = userService.getUser(email);
		
		if(userDto == null) return ResponseEntity.status(401).body(null);
		
		UserResponseModel user = modelMapper.map(userDto, UserResponseModel.class);	
		
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String id) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserDto userDto = userService.getUserByUserId(id);
		
		if(userDto == null) return ResponseEntity.notFound().build();

		UserResponseModel user = modelMapper.map(userDto, UserResponseModel.class);
		
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel user) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		
		UserResponseModel savedUser = modelMapper.map(createdUser, UserResponseModel.class);
		
		return ResponseEntity.ok().body(savedUser);
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<UserResponseModel> updateUser(
														@PathVariable String id, 
														@RequestBody UserRequestModel user, 
														Authentication auth) {
		
		String email = auth.getName();
		
		UserDto userDto2 = userService.getUser(email);
		
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		UserDto updatedUser = userService.updateUser(id, userDto, userDto2.getEmail());
		
		UserResponseModel savedUser = modelMapper.map(updatedUser, UserResponseModel.class);
		
		return ResponseEntity.ok().body(savedUser);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<OperationStatusModel> deleteUser(@PathVariable String id, Authentication auth) {
		
		String email = auth.getName();
		UserDto userDto2 = userService.getUser(email);
		
		OperationStatusModel result = new OperationStatusModel();
		
		result.setOperationName(RequestOperationNames.DELETE.name());
		
		userService.deleteUser(id, userDto2.getEmail());
	
		result.setOperationResult(RequestOperationStatus.OK.name());

		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseModel>> getUsers(
						@RequestParam(value="page", defaultValue="0") int page,
						@RequestParam(value="limit", defaultValue="25") int limit) {
		
		List<UserResponseModel> users = new ArrayList<>();
		
		List<UserDto> usersDto = userService.getUsers(page, limit);
		
		for(UserDto userDto: usersDto ) {
			
			ModelMapper modelMapper = new ModelMapper();
			
			UserResponseModel userModel = modelMapper.map(userDto, UserResponseModel.class);
			
			users.add(userModel);
		}
		
		return ResponseEntity.ok().body(users);
	}
	
	

}
