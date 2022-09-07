package com.skillstorm.spyglassapi.services.interfaces;

import com.skillstorm.spyglassapi.models.dbSet.Role;

import java.util.Optional;

public interface RoleService extends GenericService<Role, Long>{
    Optional<Role> findByName(String name);
}
