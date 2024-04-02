package com.example.kitchendesign.dto.userDTO;

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
    @Range(min = 3, max = 16)
    @JsonProperty("username")
    private String username;


    @NotBlank
    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;


    @NotBlank
    @NotEmpty
    @JsonProperty("phoneNumber")
    private String phoneNumber;


    @NotBlank
    @NotEmpty
    @Range(min = 8, max = 28)
    @JsonProperty("password")
    private String password;
}
