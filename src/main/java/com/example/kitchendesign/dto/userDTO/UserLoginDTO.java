package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.validator.annotation.ValidPassword;
import com.example.kitchendesign.validator.annotation.ValidPhoneNumber;
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
public class UserLoginDTO {

    @NotBlank(groups = User.LoginWithUserName.class)
    @NotEmpty(groups = User.LoginWithUserName.class)
    @JsonProperty("username")
    private String username;


    @NotBlank(groups = User.LoginWithEmail.class)
    @NotEmpty(groups = User.LoginWithEmail.class)
    @Email(groups = User.LoginWithEmail.class)
    @JsonProperty("email")
    private String email;


    @NotBlank(groups = User.LoginWithPhoneNumber.class)
    @NotEmpty(groups = User.LoginWithPhoneNumber.class)
    @ValidPhoneNumber(groups = User.LoginWithPhoneNumber.class)
    @JsonProperty("phoneNumber")
    private String phoneNumber;


    @NotBlank
    @NotEmpty
    @ValidPassword(message = "Incorrect password")
    @JsonProperty("password")
    private String password;
}
