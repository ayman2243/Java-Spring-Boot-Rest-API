package com.mondiamedia.tech.task.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mondiamedia.tech.task.config.SpringApplicationContext;
import com.mondiamedia.tech.task.models.requests.UserLoginRequestModel;
import com.mondiamedia.tech.task.models.responses.UserLoginResponseModel;
import com.mondiamedia.tech.task.services.UserService;
import com.mondiamedia.tech.task.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
			HttpServletResponse res) throws AuthenticationException{
		
		try{
			
			UserLoginRequestModel creds = new ObjectMapper()
											  .readValue(req.getReader(), UserLoginRequestModel.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(),
							creds.getPassword(),
							new ArrayList<>()
						)
					);
		}catch(IOException e){
			
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(
											HttpServletRequest req, 
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		
		String userName = ((User) auth.getPrincipal()).getUsername();
		
		String token = Jwts.builder()
						.setSubject(userName)
						.setExpiration(new Date(System.currentTimeMillis() + SecuirtyConstants.EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, SecuirtyConstants.getTokenSecret())
						.compact();
		
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDto = userService.getUser(userName);
				
		res.addHeader(SecuirtyConstants.HEADER_STRING,  SecuirtyConstants.TOKEN_PREFIX + token);
		res.addHeader("UserId", userDto.getUserId());
		
		UserLoginResponseModel user = new UserLoginResponseModel();
		user.setToken(SecuirtyConstants.TOKEN_PREFIX + token);
		user.setUserId(userDto.getUserId());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.getWriter().write(new ObjectMapper().writeValueAsString(user));
	}
	
	
}
