package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.validator.annotation.ValidPassword;
import com.example.kitchendesign.validator.annotation.ValidPhoneNumber;
import com.example.kitchendesign.validator.annotation.ValidUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @NotBlank
    @NotEmpty
    @ValidUsername
    @JsonProperty("username")
    private String username;


    @NotBlank
    @NotEmpty
    @ValidPassword
    @JsonProperty("password")
    private String password;


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
}
