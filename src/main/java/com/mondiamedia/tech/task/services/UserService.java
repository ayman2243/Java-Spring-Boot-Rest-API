/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.mondiamedia.tech.task.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto user, String authUserEmail);
	void deleteUser(String userId, String authUserEmail);
	List<UserDto> getUsers(int page, int limit);
}
