package com.parfait.springbindingresultjson.adder.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.parfait.springbindingresultjson.adder.model.AdderRequest;

@Component
public class AdderRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AdderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        AdderRequest request = AdderRequest.class.cast(o);

        if (request.getA() == null) {
            errors.rejectValue("a", "field.required");
        }
        if (request.getB() == null) {
            errors.rejectValue("b", "field.required");
        }
    }
}
