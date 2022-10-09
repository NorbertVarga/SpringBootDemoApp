package com.NorbertVarga.SpringBootDemoApp.validation;

import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductUpdateCommandValidator implements Validator {

    private final SharedValidationService validationService;

    @Autowired
    public ProductUpdateCommandValidator(SharedValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductUpdateCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductUpdateCommand command = (ProductUpdateCommand) target;

        if(!validationService.isStringEmpty(command.getName())) {
            if(command.getName().length() < 3 || command.getName().length() > 80) {
                errors.rejectValue("name", "product.name.length");
            }
        }


    }
}
