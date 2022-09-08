package com.skillstorm.spyglassapi.services.implementions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util.JwtUtil;
import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.models.dbSet.User;
import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;
import com.skillstorm.spyglassapi.models.dtos.outgoing.UserResponseDto;
import com.skillstorm.spyglassapi.models.generic.TokenData;
import com.skillstorm.spyglassapi.services.interfaces.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class JwtServiceImpl extends GenericRepositoryImpl<User, Long> implements JwtService, UserDetailsService {
    private AuthRepository authRepository;
    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    public JwtServiceImpl(
            AuthRepository authRepository
    ) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public AuthResult authenticate(LoginRequestDto loginRequestDto) throws Exception {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AuthResult authResult = new AuthResult();

        User existingUser = authRepository.findUserByEmail(loginRequestDto.getEmail());
        if (existingUser == null) {
            authResult.setError(new Error(400, HttpStatus.BAD_REQUEST, "Invalid login request!"));
            authResult.setSuccess(false);
            return authResult;
        }

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        //authenticate login info
        authenticator(email, password);
        UserDetails userDetails = loadUserByUsername(email);
        TokenData tokens = getTokens(existingUser);

        UserResponseDto userResponseDto = mapper.convertValue(existingUser, UserResponseDto.class);

        authResult.setUser(userResponseDto);
        authResult.setTokenData(tokens);

        return authResult;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = authRepository.findUserByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username");
        }
    }

    private TokenData getTokens(User user) {
//        org.springframework.security.core.userdetails.User
//                springUser = new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                getAuthority(user)
//        );

        UserDetails springUser = loadUserByUsername(user.getEmail());
        TokenData tokens = new TokenData();
        tokens.setJwtToken(jwtUtil.generateToken(springUser, 5));
        tokens.setRefreshToken(jwtUtil.generateToken(springUser, 30));

        return tokens;
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    private void authenticator(String userName, String userPassword) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
    }
}
