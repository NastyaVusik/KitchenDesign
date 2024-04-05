package com.example.kitchendesign.validator.phoneNumberValidator;

import com.example.kitchendesign.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PhoneNumberConstraintValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private final UserService userService;

    @Override
    public void initialize(ValidPhoneNumber arg1) {
        ConstraintValidator.super.initialize(arg1);
    }


    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

        if(userService.isPhoneNumberAlreadyUsed(phoneNumber)){
            return false;
        }
        else {
            return true;
        }
    }
}
