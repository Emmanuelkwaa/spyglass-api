package com.skillstorm.spyglassapi.unitOfWork;

import com.skillstorm.spyglassapi.services.interfaces.GoalService;

public interface IUnitOfWork {
    GoalService goal();
}
