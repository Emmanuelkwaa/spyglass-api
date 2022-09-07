package com.skillstorm.spyglassapi.services.implementions;

import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.models.dbSet.Role;
import com.skillstorm.spyglassapi.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl extends GenericRepositoryImpl<Role, Long> implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findGoalByName(name);
    }
}
