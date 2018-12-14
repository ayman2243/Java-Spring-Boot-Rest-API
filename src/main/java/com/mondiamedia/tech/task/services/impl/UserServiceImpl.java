/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mondiamedia.tech.task.entities.UserEntity;
import com.mondiamedia.tech.task.repositories.UserRepository;
import com.mondiamedia.tech.task.services.UserService;
import com.mondiamedia.tech.task.shared.Utils;
import com.mondiamedia.tech.task.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Email already exists!");
		
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		userEntity.setUserId(utils.genrateRandonString(50));
		
		UserEntity savedUser = userRepository.save(userEntity);
		
		UserDto userDto = modelMapper.map(savedUser, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), 
						userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException("404 not found");
		
		UserDto userDto =  modelMapper.map(userEntity, UserDto.class);
		
		return userDto;
		
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException("404 not found");
		
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user, String authUserEmail) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity.getEmail() != authUserEmail) throw new UsernameNotFoundException("401 unauthorized");
			
		userEntity.setFirstName(user.getFirstName());
		
		userEntity.setLastName(user.getLastName());
		
		UserEntity updatedUserEntity = userRepository.save(userEntity);
		
		UserDto userDto = modelMapper.map(updatedUserEntity, UserDto.class);
		
		return userDto;
		
	}

	@Override
	public void deleteUser(String userId, String authUserEmail) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity.getEmail() != authUserEmail) throw new UsernameNotFoundException("401 unauthorized");
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		List<UserDto> users = new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> usersPage = userRepository.findAllOrderByIdDesc(pageableRequest);
		
		List<UserEntity> usersEntities = usersPage.getContent();
		
		for(UserEntity userEntity: usersEntities) {
		
			ModelMapper modelMapper = new ModelMapper();
			
			UserDto userDto = modelMapper.map(userEntity, UserDto.class);
			
			users.add(userDto);
		}
		
		return users;
	}

}
