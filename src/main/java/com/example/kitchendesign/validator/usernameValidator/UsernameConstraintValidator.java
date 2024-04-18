package com.example.kitchendesign.validator.usernameValidator;

import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.validator.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsernameConstraintValidator implements ConstraintValidator<ValidUsername, String> {

    private final UserService userService;

    @Override
    public void initialize(ValidUsername arg1) {
        ConstraintValidator.super.initialize(arg1);
    }


    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if(userService.isUsernameAlreadyUsed(username)){
            return false;
        }
        else {
            return true;
        }
    }
}
