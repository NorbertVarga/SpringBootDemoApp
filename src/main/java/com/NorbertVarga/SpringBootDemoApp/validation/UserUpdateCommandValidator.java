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

        if (!validationService.isStringEmpty(command.getEmail())) {
            if (!command.getEmail().matches("^(.+)@(.+)$")) {
                errors.rejectValue("email", "email.valid");
            }
            if (validationService.findUserByEmail(command.getEmail()) != null) {
                errors.rejectValue("email", "email.exist");
            }
        }

        if (!validationService.isStringEmpty(command.getFirstName())) {
            if (command.getFirstName().length() < 3 || command.getFirstName().length() > 30) {
                errors.rejectValue("firstName", "firstname.length");
            }
        }

        if (!validationService.isStringEmpty(command.getLastName())) {
            if (command.getLastName().length() < 3 || command.getLastName().length() > 30) {
                errors.rejectValue("lastName", "lastname.length");
            }
        }

        if (!validationService.isStringEmpty(command.getPassword())) {
            if (command.getPassword().length() < 8 || command.getPassword().length() > 30) {
                errors.rejectValue("password", "password.length");
            }
        }
    }
}
