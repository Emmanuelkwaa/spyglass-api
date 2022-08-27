package com.skillstorm.spyglassapi.services.implementions;

import com.skillstorm.spyglassapi.data.repositories.GenericRepositoryImpl;
import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.models.Goal;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GoalServiceImpl extends GenericRepositoryImpl<Goal, Long> implements GoalService {
    private GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        super(goalRepository);
        this.goalRepository = goalRepository;
    }
}
