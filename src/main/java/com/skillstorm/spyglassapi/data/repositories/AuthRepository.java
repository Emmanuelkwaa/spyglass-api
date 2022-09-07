package com.skillstorm.spyglassapi.data.repositories;

import com.skillstorm.spyglassapi.models.dbSet.User;

public interface AuthRepository extends GenericRepository<User, Long> {
    User findUserByEmail(String email);
}
