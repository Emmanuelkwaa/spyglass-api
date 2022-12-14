package com.skillstorm.spyglassapi.data.repositories;

import com.skillstorm.spyglassapi.models.dbSet.Goal;

import java.util.Optional;

public interface GoalRepository extends GenericRepository<Goal,Long> {
    Optional<Goal> findGoalByName(String name);
}
