/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.shared;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "01234567890azsxdcfvgbhnjmklpoiuytrewqAZSXDCFVGBHNJMKLPOIUYTREWQ_-";
	
	public String genrateRandonString(int length) {
		return genrateRandomString(length);
	}
	
	public String uniqeVideoName() {
		return UUID.randomUUID().toString();
	}
	
	private String genrateRandomString(int length) {
		// Date date = new Date();
		StringBuilder genKey = new StringBuilder(length);
		
		for(int i = 0; i < length; i++) {
			genKey.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(genKey);
	}
}
