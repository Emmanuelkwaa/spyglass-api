package com.skillstorm.spyglassapi.STATIC_DETAILS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class SD {
    @Autowired
    private Environment environment;
    public final static String Role_Admin = "ADMIN";
    public final static String Role_User = "APP_USER";
    public final static String LOGIN = "/api/v1/auth/login";
    public final String secret = environment.getProperty("jwt.secret");
    public final static String JWT_ISSUER = "http://localhost:4200";
}
