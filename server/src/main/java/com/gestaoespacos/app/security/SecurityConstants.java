package com.gestaoespacos.app.security;

public class SecurityConstants {
	public static final String SECRET = "c3VwZXJTZWNyZXRLZXk=";
	public static final long EXPIRATION_TIME =  25920000000L; // 30days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/public/users/login";
	public static final String ACESSABLE= "/public/users/**";
}


// https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/