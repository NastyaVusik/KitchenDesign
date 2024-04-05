package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.validator.passwordValidator.ValidPassword;
import com.example.kitchendesign.validator.phoneNumberValidator.ValidPhoneNumber;
import com.example.kitchendesign.validator.usernameValidator.ValidUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank
    @NotEmpty
    @ValidUsername
    @JsonProperty("username")
    private String username;


    @NotBlank
    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;


    @NotBlank
    @NotEmpty
    @ValidPhoneNumber
    @JsonProperty("phoneNumber")
    private String phoneNumber;


    @NotBlank
    @NotEmpty
    @ValidPassword
    @JsonProperty("password")
    private String password;
}
