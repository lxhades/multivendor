package com.auth_service.adapters.in.web.controller;

import com.auth_service.adapters.in.web.dto.request.RegisterRequest;
import com.auth_service.application.command.RegisterUserCommand;
import com.auth_service.application.port.in.RegisterUserResult;
import com.auth_service.application.port.out.UserRepository;
import com.auth_service.application.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterUserController {
    private final RegisterUserUseCase registerUserUseCase;
    public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResult> register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterUserCommand registerUserCommand=new RegisterUserCommand(
                registerRequest.getEmail(),
                registerRequest.getPhone(),
                registerRequest.getPassword());

        RegisterUserResult registerUserResult= registerUserUseCase.execute(registerUserCommand);
        return ResponseEntity.ok(registerUserResult);
    }

}
