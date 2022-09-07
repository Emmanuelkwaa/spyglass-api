package com.skillstorm.spyglassapi.data.repositories;

import com.skillstorm.spyglassapi.models.dbSet.Role;
import java.util.Optional;

public interface RoleRepository extends GenericRepository<Role,Long>{
    Optional<Role> findRoleByName(String name);
}
