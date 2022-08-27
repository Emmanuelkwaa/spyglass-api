package com.skillstorm.spyglassapi.unitOfWork;

import com.skillstorm.spyglassapi.data.repositories.GoalRepository;
import com.skillstorm.spyglassapi.services.implementions.GoalServiceImpl;
import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitOfWork implements IUnitOfWork{
    private GoalService goalService;

    @Autowired
    public UnitOfWork(GoalRepository goalRepository) {
        this.goalService = new GoalServiceImpl(goalRepository);
    }

    @Override
    public GoalService goal() {
        return this.goalService;
    }
}
