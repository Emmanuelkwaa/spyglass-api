package com.skillstorm.spyglassapi.services.implementions;

import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.models.dbSet.Category;
import com.skillstorm.spyglassapi.models.dbSet.Goal;
import com.skillstorm.spyglassapi.models.dbSet.User;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GoalServiceImpl extends GenericRepositoryImpl<Goal, Long> implements GoalService {
    private GoalRepository goalRepository;
    private AuthRepository authRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, AuthRepository authRepository) {
        super(goalRepository);
        this.goalRepository = goalRepository;
        this.authRepository = authRepository;
    }

    @Override
    public Optional<Goal> findByName(String name) {
        return goalRepository.findGoalByName(name);
    }

    @Override
    public Goal createGoal(Goal goal) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserName = "";
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            currentUserName = authentication.getName();
//        }

        String username = "";
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        User user = authRepository.findUserByEmail(username);
        goal.setUser(user);
        goal.setCategory(new Category(Long.valueOf(1), "Personal"));

        return goalRepository.save(goal);
    }
}
