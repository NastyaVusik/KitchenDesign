package com.example.kitchendesign.dto.emailSenderDTO;

import com.example.kitchendesign.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleEmailDTO {

    @NotBlank
    @NotEmpty
    private User user;

    @NotBlank
    @NotEmpty
    private String subject;

    @NotBlank
    @NotEmpty
    private String message;

}
