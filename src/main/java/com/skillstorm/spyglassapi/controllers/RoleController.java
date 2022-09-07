package com.skillstorm.spyglassapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglassapi.models.dbSet.Role;
import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import com.skillstorm.spyglassapi.models.dtos.incoming.RoleRequestDto;
import com.skillstorm.spyglassapi.models.generic.Result;
import com.skillstorm.spyglassapi.unitOfWork.IUnitOfWork;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
//@CrossOrigin("*")
@RequestMapping("api/v1/roles")
public class RoleController {
    private final IUnitOfWork unitOfWork;

    public RoleController(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<Role>> findById(@PathVariable long id) {
        Optional<Role> role = unitOfWork.role().findById(id);
        return getResultResponseEntity(role);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Result<Role>> findByName(@PathVariable String name) {
        Optional<Role> role = unitOfWork.role().findByName(name);
        return getResultResponseEntity(role);
    }

    private ResponseEntity<Result<Role>> getResultResponseEntity(Optional<Role> role) {
        Result result = new Result<Role>();

        if(role.isPresent()) {
            result.content.add(role.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.error = new Error(404, HttpStatus.NOT_FOUND, "Role not found");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Result<Role>> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Role role = mapper.convertValue(roleRequestDto, Role.class);
        Result result = new Result<Role>();

        try {
            Role createdRole = unitOfWork.role().save(role);
            if(createdRole != null) {
                result.content.add(createdRole);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }

            result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to create role");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.error = new Error(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Role>> updateRole(@PathVariable long id, @Valid @RequestBody RoleRequestDto roleRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Role role = mapper.convertValue(roleRequestDto, Role.class);
        Result result = new Result<Role>();

        try {
            Role updatedRole = unitOfWork.role().updateById(role, id);
            if(updatedRole != null) {
                result.content.add(updatedRole);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }

            result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to update role");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.error = new Error(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Role>> deleteRole(@PathVariable long id) {
        unitOfWork.role().deleteById(id);
        Optional<Role> role = unitOfWork.role().findById(id);
        boolean isDeleted = role.isPresent();
        Result result = new Result<Role>();

        if(isDeleted) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        result.error = new Error(400, HttpStatus.BAD_REQUEST, "Unable to delete role");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
