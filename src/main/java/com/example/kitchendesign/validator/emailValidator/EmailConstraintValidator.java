package com.example.kitchendesign.validator.emailValidator;

import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.validator.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

    private final UserService userService;


    @Override
    public void initialize(ValidEmail arg1) {
        ConstraintValidator.super.initialize(arg1);
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userService.isEmailAlreadyUsed(email)) {
            return false;
        } else {
            return true;
        }
    }
}
