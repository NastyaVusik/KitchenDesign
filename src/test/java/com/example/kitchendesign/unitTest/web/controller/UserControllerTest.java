package com.example.kitchendesign.unitTest.web.controller;

import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.web.controller.UserController;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@RequiredArgsConstructor
public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void registration_whenUserSaved_returnJsonAndsSendEmail(){

    }
}
