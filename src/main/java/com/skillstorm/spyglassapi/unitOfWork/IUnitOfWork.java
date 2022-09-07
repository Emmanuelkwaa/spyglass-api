package com.skillstorm.spyglassapi.unitOfWork;

import com.skillstorm.spyglassapi.services.interfaces.GoalService;
import com.skillstorm.spyglassapi.services.interfaces.RoleService;

public interface IUnitOfWork {
    GoalService goal();
    RoleService role();

}
