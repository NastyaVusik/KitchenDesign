package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.validator.passwordValidator.ValidPassword;
import com.example.kitchendesign.validator.phoneNumberValidator.ValidPhoneNumber;
import com.example.kitchendesign.validator.usernameValidator.ValidUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "username is mandatory")
    @NotEmpty
    @ValidUsername
    @JsonProperty("username")
    private String username;


    @NotBlank(message = "password is mandatory")
    @NotEmpty
    @ValidPassword
    @JsonProperty("password")
    private String password;


    @NotBlank(message = "email is mandatory")
    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;


    @NotBlank(message = "phone number is mandatory")
    @NotEmpty
    @ValidPhoneNumber
    @JsonProperty("phoneNumber")
    private String phoneNumber;
}
