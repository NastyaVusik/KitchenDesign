package com.example.kitchendesign.dto.userDTO;

import com.example.kitchendesign.entity.Role;
import com.example.kitchendesign.entity.project.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDTO {

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
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("regDate")          //????????????????????????????
    private LocalDateTime regDate;          //????????????????????????????


    @JsonProperty("projects")
    private List<Project> projects;
}
