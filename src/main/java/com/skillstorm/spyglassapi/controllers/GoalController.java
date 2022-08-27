package com.skillstorm.spyglassapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglassapi.models.Goal;
import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import com.skillstorm.spyglassapi.models.dtos.incoming.GoalRequestDto;
import com.skillstorm.spyglassapi.models.generic.Result;
import com.skillstorm.spyglassapi.unitOfWork.IUnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/goals")
public class GoalController {
    private IUnitOfWork unitOfWork;

    @Autowired
    public GoalController(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<Goal>> findById(@PathVariable long id) {
        Optional<Goal> goal = unitOfWork.goal().findById(id);
        return getResultResponseEntity(goal);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Result<Goal>> findByName(@PathVariable String name) {
        Optional<Goal> goal = unitOfWork.goal().findByName(name);
        return getResultResponseEntity(goal);
    }

    private ResponseEntity<Result<Goal>> getResultResponseEntity(Optional<Goal> goal) {
        Result result = new Result<Goal>();

        if(goal.isPresent()) {
            result.content.add(goal.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.error = new Error(404, HttpStatus.NOT_FOUND, "Goal not found");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Result<Goal>> createGoal(@Valid @RequestBody GoalRequestDto goalRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Goal goal = mapper.convertValue(goalRequestDto, Goal.class);
        Result result = new Result<Goal>();

        try {
            Goal createdGoal = unitOfWork.goal().save(goal);
            if(createdGoal != null) {
                result.content.add(createdGoal);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }

            result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to create goal");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.error = new Error(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Goal>> updateGoal(@PathVariable long id, @Valid @RequestBody GoalRequestDto goalRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Goal goal = mapper.convertValue(goalRequestDto, Goal.class);
        Result result = new Result<Goal>();

        try {
            Goal updatedGoal = unitOfWork.goal().updateById(goal, id);
            if(updatedGoal != null) {
                result.content.add(updatedGoal);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }

            result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to update goal");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.error = new Error(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Goal>> deleteGoal(@PathVariable long id) {
        unitOfWork.goal().deleteById(id);
        Optional<Goal> goal = unitOfWork.goal().findById(id);
        boolean isDeleted = goal.isPresent();
        Result result = new Result<Goal>();

        if(isDeleted) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to delete goal");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
