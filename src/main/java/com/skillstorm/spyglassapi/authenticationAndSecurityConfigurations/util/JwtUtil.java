package com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Autowired
    private Environment environment;

    public String generateToken(User user, int minutes) {
        String secret = environment.getProperty("jwt.secret");
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000))
                .withIssuer(SD.JWT_ISSUER)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        return token;
    }
}
