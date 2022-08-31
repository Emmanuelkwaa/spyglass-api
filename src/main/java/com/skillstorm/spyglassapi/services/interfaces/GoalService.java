package com.skillstorm.spyglassapi.services.interfaces;

import com.skillstorm.spyglassapi.models.dbSet.Goal;

import java.util.Optional;

public interface GoalService extends GenericService<Goal, Long> {
    Optional<Goal> findByName(String name);
}
