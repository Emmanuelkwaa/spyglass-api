package com.skillstorm.spyglassapi.unitOfWork;

import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util.JwtUtil;
import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.services.implementions.AuthServiceImpl;
import com.skillstorm.spyglassapi.services.implementions.GoalServiceImpl;
import com.skillstorm.spyglassapi.services.implementions.RoleServiceImpl;
import com.skillstorm.spyglassapi.services.interfaces.AuthService;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import com.skillstorm.spyglassapi.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UnitOfWork implements IUnitOfWork{
    private final GoalService goalService;
    private final RoleService roleService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UnitOfWork(
        GoalRepository goalRepository,
        RoleRepository roleRepository,
        AuthRepository authRepository,
        PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil
    ) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.goalService = new GoalServiceImpl(goalRepository, authRepository);
        this.roleService = new RoleServiceImpl(roleRepository);
        this.authService = new AuthServiceImpl(authRepository,
                roleRepository,
                this.passwordEncoder,
                this.jwtUtil);
    }

    @Override
    public GoalService goal() {
        return this.goalService;
    }

    @Override
    public RoleService role() {
        return this.roleService;
    }

    @Override
    public AuthService auth() {
        return this.authService;
    }
}
