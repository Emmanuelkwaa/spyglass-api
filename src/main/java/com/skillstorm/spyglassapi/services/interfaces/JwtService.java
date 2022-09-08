package com.skillstorm.spyglassapi.services.interfaces;

import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtService {
    AuthResult authenticate(LoginRequestDto loginRequestDto) throws Exception;
}
