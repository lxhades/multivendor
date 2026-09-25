package com.auth_service.application.usecase;

import com.auth_service.application.command.RegisterUserCommand;
import com.auth_service.application.port.in.RegisterUserResult;
import com.auth_service.application.port.out.PasswordHasher;
import com.auth_service.application.port.out.UserRepository;
import com.auth_service.domain.exception.EmailAlreadyExistsException;
import com.auth_service.domain.model.aggregate.User;
import com.auth_service.domain.model.vo.Email;
import com.auth_service.domain.model.vo.Phone;

import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    private final PasswordHasher passwordHasher;
    private final UserRepository userRepository;
    public RegisterUserUseCase(PasswordHasher passwordHasher, UserRepository userRepository) {
        this.passwordHasher = passwordHasher;
        this.userRepository = userRepository;
    }

    public RegisterUserResult execute(RegisterUserCommand registerUserCommand){
        Email email = Email.of(registerUserCommand.email());
        if(userRepository.findByEmail(email).isPresent()){
            throw new EmailAlreadyExistsException(registerUserCommand.email());
        }
        User savedUser=userRepository.save(User.register(email,
                        new Phone(registerUserCommand.phone()),
                        passwordHasher.hash(registerUserCommand.password())));
        return new RegisterUserResult(
                savedUser.getUserId().value(),
                savedUser.getEmail().getValue(),
                savedUser.getPhone().value(),
                savedUser.getStatus()
        );

    }
}
