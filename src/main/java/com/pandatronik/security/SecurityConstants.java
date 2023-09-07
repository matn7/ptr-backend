package com.pandatronik.security;

public class SecurityConstants {

    // todo: all this values in properties file and cypher them

    public static final String LOGIN_URL = "/api/v1/login";

    public static final String SIGN_UP_URLS = "/api/v1/register";

    public static final String FORGOT_PASSWORD_URL = "/api/v1/forgotpassword";

    public static final String CHANGE_PASSWORD_URL = "/api/v1/changepassword";

    public static final String ACTIVATE_USER_URL = "/api/v1/activatenewuser";

    public static final String H2_URL = "h2-console/**";

    public static final String SECRET = "D8279C398FD697394AB756621A4A8218855F7369B4DF7D6EEF9C3D93A853FEDFB71BF92255ACE43CDBB4D93";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final long EXPIRATION_TIME = 600_000;

}