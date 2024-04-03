package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.entity.Role;
import com.example.kitchendesign.entity.project.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @NotBlank
    @NotEmpty
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotEmpty
    @Range(min = 3, max = 16)
    @JsonProperty("username")
    private String username;


    @NotBlank
    @NotEmpty
    @Range(min = 8, max = 28)
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
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonProperty("regDate")          //????????????????????????????
    private LocalDateTime regDate;          //????????????????????????????


    @JsonProperty("projects")
    private List<Project> projects;
}
