package com.NorbertVarga.SpringBootDemoApp.validation;

import com.NorbertVarga.SpringBootDemoApp.dto.userAccount.UserCreateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserCreateCommandValidator implements Validator {

    private final SharedValidationService validationService;
    private static final Logger logger = LoggerFactory.getLogger(UserCreateCommandValidator.class);

    @Autowired
    public UserCreateCommandValidator(SharedValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateCommand command = (UserCreateCommand) target;

        if (!command.getEmail().matches("^(.+)@(.+)$")) {
            errors.rejectValue("email", "email.valid");
            logger.error("** FAILED REGISTER: " + command.getEmail() + ": FORMAT INVALID");
        }

        if (validationService.findUserByEmail(command.getEmail()) != null) {
            errors.rejectValue("email", "email.exist");
            logger.error("** FAILED REGISTER: " + command.getEmail() + ": ALREADY EXIST");
        }
    }
}
