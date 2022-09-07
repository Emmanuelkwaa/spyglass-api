package com.skillstorm.spyglassapi.controllers;

import com.skillstorm.spyglassapi.models.dtos.incoming.LoginRequestDto;
import com.skillstorm.spyglassapi.models.dtos.incoming.UserRequestDto;
import com.skillstorm.spyglassapi.models.dtos.outgoing.AuthResult;
import com.skillstorm.spyglassapi.unitOfWork.IUnitOfWork;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
//@CrossOrigin("*")
@RequestMapping("api/v1/auth")
public class AuthController {
    private final IUnitOfWork unitOfWork;

    public AuthController(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResult> register (@Valid @RequestBody UserRequestDto userRequestDto) {
        AuthResult authResult = unitOfWork.auth().saveUser(userRequestDto);
        if(authResult.isSuccess()) {
            return new ResponseEntity<>(authResult, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(authResult, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResult> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        AuthResult authResult = unitOfWork.auth().authenticate(loginRequestDto);
        if (authResult.isSuccess()){
            return new ResponseEntity<>(authResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(authResult, HttpStatus.BAD_REQUEST);
    }
}
