package com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtil {
    @Autowired
    private Environment environment;

    public String generateToken(User user, int minutes, HttpServletRequest request) {
        String secret = environment.getProperty("jwt.secret");
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        return token;
    }
}
