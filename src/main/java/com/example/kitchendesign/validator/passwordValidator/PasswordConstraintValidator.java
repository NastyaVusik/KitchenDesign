package com.example.kitchendesign.validator.passwordValidator;

import com.example.kitchendesign.validator.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.passay.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword arg1) {
        ConstraintValidator.super.initialize(arg1);
    }


    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        Properties properties = new Properties();
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("passay.properties");
        properties.load(inputStream);
        MessageResolver messageResolver = new PropertiesMessageResolver(properties);

        PasswordValidator passwordValidator = new PasswordValidator(messageResolver, Arrays.asList(

                // 1) length between 8 and 16 characters
                new LengthRule(8, 16),

                // 2) at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // 3) at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // 4) at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // 5) at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // 6) no whitespace
                new WhitespaceRule(),

                // 7) reject passwords, that contain a sequence of >=5 character alphabetical
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),

                // 8) reject passwords, that contain a sequence of >= 5 character numerical
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));

        RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));

        if (ruleResult.isValid()) {
            return true;
        }

        List<String> message = passwordValidator.getMessages(ruleResult);
        String messageTemplate = String.join(",", message);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
