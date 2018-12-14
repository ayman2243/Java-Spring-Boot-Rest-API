package com.mondiamedia.tech.task.security;

import com.mondiamedia.tech.task.config.AppConfig;
import com.mondiamedia.tech.task.config.SpringApplicationContext;

public class SecuirtyConstants {
	public static final long EXPIRATION_TIME = (3600000 * 24 * 100);  //100 Day
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	
	public static String getTokenSecret() {
		AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("AppConfig");
		return appConfig.getTokenSecret();
	}
}
