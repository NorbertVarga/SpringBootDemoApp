package com.NorbertVarga.SpringBootDemoApp.validation;

import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateCommandValidator implements Validator {

    private final SharedValidationService validationService;

    @Autowired
    public UserUpdateCommandValidator(SharedValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateCommand command = (UserUpdateCommand) target;

        if (command.getEmail() != null && !command.getEmail().isEmpty() && !command.getEmail().isBlank()) {
            if (!command.getEmail().matches("^(.+)@(.+)$")) {
                errors.rejectValue("email", "email.valid");
            }
            if (validationService.findUserByEmail(command.getEmail()) != null) {
                errors.rejectValue("email", "email.exist");
            }
        }

        if (command.getFirstName() != null && !command.getFirstName().isEmpty() && !command.getFirstName().isBlank()) {
            if (command.getFirstName().length() < 3 || command.getFirstName().length() > 30) {
                errors.rejectValue("firstName", "firstname.length");
            }
        }

        if (command.getLastName() != null && !command.getLastName().isEmpty() && !command.getLastName().isBlank()) {
            if (command.getLastName().length() < 3 || command.getLastName().length() > 30) {
                errors.rejectValue("lastName", "lastname.length");
            }
        }

        if (command.getPassword() != null && !command.getPassword().isEmpty() && !command.getPassword().isBlank()) {
            if (command.getPassword().length() < 8 || command.getPassword().length() > 30) {
                errors.rejectValue("password", "password.length");
            }
        }
    }
}
