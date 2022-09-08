package com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util.JwtUtil;
import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;
import com.skillstorm.spyglassapi.models.dtos.outgoing.UserResponseDto;
import com.skillstorm.spyglassapi.models.generic.TokenData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthRepository authRepository, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        InputStream reqBody = null;
        LoginRequestDto loginRequestDto = null;
        try {
            reqBody = request.getInputStream();
            loginRequestDto = mapper.readValue(reqBody, LoginRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        String access_token = jwtUtil.generateToken(user, 5);
        String refresh_token = jwtUtil.generateToken(user, 30);

        AuthResult authResult = new AuthResult();
        com.skillstorm.spyglassapi.models.dbSet.User userByEmail = authRepository.findUserByEmail(user.getUsername());
        TokenData tokens = new TokenData();

        UserResponseDto userResponseDto = mapper.convertValue(userByEmail, UserResponseDto.class);

        tokens.setJwtToken(access_token);
        tokens.setRefreshToken(refresh_token);
        authResult.setUser(userResponseDto);
        authResult.setTokenData(tokens);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().print(mapper.writeValueAsString(authResult));
    }
}
