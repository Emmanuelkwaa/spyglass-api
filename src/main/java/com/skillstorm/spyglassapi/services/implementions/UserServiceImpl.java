package com.skillstorm.spyglassapi.services.implementions;

import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.RoleRepository;
import com.skillstorm.spyglassapi.data.repositories.UserRepository;
import com.skillstorm.spyglassapi.models.dbSet.Role;
import com.skillstorm.spyglassapi.models.dbSet.User;
import com.skillstorm.spyglassapi.services.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserServiceImpl extends GenericRepositoryImpl<User, Long> implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        Role role = roleRepository.findGoalByName(SD.Role_User).get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
