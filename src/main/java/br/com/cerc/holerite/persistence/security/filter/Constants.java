package br.com.cerc.holerite.persistence.security.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Constants {
	public final static String SECRET_KEY = "r37DkpFx0GOShYGAyAJ0ZRB5VNODgmrc";
	public final static String HEADER_STRING = "Authorization";
	public final static String TOKEN_PREFIX = "Bearer ";
	public final static int expTime = 86400000; //1 Day
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("senha"));
	}
}
