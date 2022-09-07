package com.skillstorm.spyglassapi.unitOfWork;

import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.services.implementions.GoalServiceImpl;
import com.skillstorm.spyglassapi.services.implementions.RoleServiceImpl;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import com.skillstorm.spyglassapi.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitOfWork implements IUnitOfWork{
    private final GoalService goalService;
    private final RoleService roleService;

    @Autowired
    public UnitOfWork(GoalRepository goalRepository, RoleRepository roleRepository) {
        this.goalService = new GoalServiceImpl(goalRepository);
        this.roleService = new RoleServiceImpl(roleRepository);
    }

    @Override
    public GoalService goal() {
        return this.goalService;
    }

    @Override
    public RoleService role() {
        return this.roleService;
    }
}
