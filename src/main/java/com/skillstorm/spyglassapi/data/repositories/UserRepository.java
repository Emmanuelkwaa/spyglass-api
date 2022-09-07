package com.skillstorm.spyglassapi.data.repositories;

import com.skillstorm.spyglassapi.models.dbSet.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long> {
    User findUserByEmail(String email);
}
