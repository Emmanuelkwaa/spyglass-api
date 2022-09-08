package com.skillstorm.spyglassapi.services.interfaces;

import com.skillstorm.spyglassapi.models.dbSet.User;
import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.incoming.UserRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;

public interface AuthService extends GenericService<User, Long> {
    User findByEmail(String email);
    AuthResult saveUser(UserRequestDto user);
}
