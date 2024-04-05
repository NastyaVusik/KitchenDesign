package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO {          //????????????????????????????

    @NotBlank
    @NotEmpty
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotEmpty
    @JsonProperty("username")
    private String username;


    @NotBlank
    @NotEmpty
    @JsonProperty("password")
    private String password;


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
    @JsonProperty("roles")          //????????????????????????????
    private Set<Role> roles;


    @NotBlank
    @NotEmpty
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("regDate")          //????????????????????????????
    private LocalDateTime regDate;          //????????????????????????????

}
