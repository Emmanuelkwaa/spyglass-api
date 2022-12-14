package com.skillstorm.spyglassapi.services.implementions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter.JwtAuthenticationFilter;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util.JwtUtil;
import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import com.skillstorm.spyglassapi.models.dbSet.Role;
import com.skillstorm.spyglassapi.models.dbSet.User;
import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.incoming.UserRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;
import com.skillstorm.spyglassapi.models.dtos.outgoing.UserResponseDto;
import com.skillstorm.spyglassapi.models.generic.TokenData;
import com.skillstorm.spyglassapi.services.interfaces.AuthService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl extends GenericRepositoryImpl<User, Long> implements AuthService {
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(
            AuthRepository authRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        super(authRepository);
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User findByEmail(String email) {
        return authRepository.findUserByEmail(email);
    }

    @Override
    public AuthResult saveUser(UserRequestDto userRequestDto) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = mapper.convertValue(userRequestDto, User.class);
        AuthResult authResult = new AuthResult();

        User existingUser = authRepository.findUserByEmail(user.getEmail());
        if (existingUser != null) {
            authResult.setError(new Error(400, HttpStatus.BAD_REQUEST, "Bad Request"));
            authResult.setSuccess(false);
            return authResult;
        }

        Role role = roleRepository.findRoleByName(SD.Role_User).get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = authRepository.save(user);

        //create jwt token
        TokenData tokens = getTokens(newUser);
        UserResponseDto userResponseDto = mapper.convertValue(newUser, UserResponseDto.class);

        authResult.setUser(userResponseDto);
        authResult.setTokenData(tokens);

        return authResult;
    }

    private TokenData getTokens(User user) {
        org.springframework.security.core.userdetails.User
                springUser = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthority(user)
        );

        //UserDetails springUser = loadUserByUsername(user.getEmail());
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
}
