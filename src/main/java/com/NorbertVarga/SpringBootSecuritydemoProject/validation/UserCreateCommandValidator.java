package com.NorbertVarga.SpringBootSecuritydemoProject.validation;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserCreateCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserCreateCommandValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateCommand command = (UserCreateCommand) target;
        if (command.getEmail() == null || command.getEmail().isBlank()) {
            errors.rejectValue("email", "email.empty");
        }
        if (!command.getEmail().matches("^(.+)@(.+)$")) {
            errors.rejectValue("email", "email.valid");
        }

        if (command.getFirstName() == null || command.getFirstName().isBlank()) {
            errors.rejectValue("firstName", "firstname.empty");
        }

        if (command.getLastName() == null || command.getLastName().isBlank()) {
            errors.rejectValue("lastName", "lastname.empty");
        }

        if (command.getPassword() == null || command.getPassword().isBlank()) {
            errors.rejectValue("password", "password.empty");
        }
        if (command.getPassword().length() < 8) {
            errors.rejectValue("password", "password.length");
        }
    }
}
