package com.example.kitchendesign.validator.phoneNumberValidator;

import com.example.kitchendesign.validator.annotation.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.kitchendesign.validator.phoneNumberValidator.CheckedPhoneNumber;

@Component
@AllArgsConstructor
public class PhoneNumberConstraintValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public void initialize(ValidPhoneNumber arg1) {
        ConstraintValidator.super.initialize(arg1);
    }


    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

        CheckedPhoneNumber checkedPhoneNumber = new CheckedPhoneNumber();

        if (!checkedPhoneNumber.validateAndNormalizePhoneNumber(phoneNumber).isEmpty()){
                return true;
            }

        return false;
    }
}

