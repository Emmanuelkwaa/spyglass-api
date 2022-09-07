package com.skillstorm.spyglassapi.services.interfaces;

import com.skillstorm.spyglassapi.models.dbSet.User;

import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User findByEmail(String email);
    User saveUser(User user);
}
