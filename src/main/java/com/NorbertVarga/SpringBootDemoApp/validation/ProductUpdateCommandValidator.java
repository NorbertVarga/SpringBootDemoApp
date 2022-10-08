package com.NorbertVarga.SpringBootDemoApp.validation;

import com.NorbertVarga.SpringBootDemoApp.dto.product.ProductUpdateCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductUpdateCommandValidator implements Validator {

    private final SharedValidationService validationService;

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

    }
}
