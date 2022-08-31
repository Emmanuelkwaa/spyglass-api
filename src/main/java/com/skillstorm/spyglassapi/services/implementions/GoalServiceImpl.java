package com.skillstorm.spyglassapi.services.implementions;

import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.models.dbSet.Goal;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GoalServiceImpl extends GenericRepositoryImpl<Goal, Long> implements GoalService {
    private GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        super(goalRepository);
        this.goalRepository = goalRepository;
    }

    @Override
    public Optional<Goal> findByName(String name) {
        return goalRepository.findGoalByName(name);
    }
}
