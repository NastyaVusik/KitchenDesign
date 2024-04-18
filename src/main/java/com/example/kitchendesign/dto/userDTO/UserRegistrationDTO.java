package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.entity.Role;
import com.example.kitchendesign.validator.annotation.ValidEmail;
import com.example.kitchendesign.validator.annotation.ValidPassword;
import com.example.kitchendesign.validator.annotation.ValidPhoneNumber;
import com.example.kitchendesign.validator.annotation.ValidUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

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
    @ValidEmail
    @JsonProperty("email")
    private String email;


    @NotBlank(message = "phone number is mandatory")
    @NotEmpty
    @ValidPhoneNumber
    @JsonProperty("phoneNumber")
    private String phoneNumber;


//    @NotBlank
//    @NotEmpty
//    @JsonProperty("roles")          //????????????????????????????
//    private Set<Role> roles;
}
