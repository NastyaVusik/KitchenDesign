package com.example.kitchendesign.unitTest.web.controller;

import com.example.kitchendesign.dto.userDTO.UserGetDTO;
import com.example.kitchendesign.dto.userDTO.UserRegistrationDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.service.emailService.DefaultEmailService;
import com.example.kitchendesign.web.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private GeneralMapper generalMapper;

    @MockBean
    private DefaultEmailService defaultEmailService;

    private List<User> userList;


    @BeforeEach
    void setUp() {
        this.generalMapper = GeneralMapper.MAPPER;
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, generalMapper, defaultEmailService)).build();

        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077316", null, LocalDateTime.parse("2019-10-31T23:59:59.0000"), null));
        this.userList.add(new User(2L, "cat2", "123QWErty&", "cat2@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-11-30T23:59:59.0000"), null));
        this.userList.add(new User(3L, "cat3", "123QWErty?", "cat3@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

    }


    @Test
    void registration_whenUserSaved_returnJsonStatus201AndsSendEmail() throws Exception {

        //        Given
        //        Parameters for userRegistrationDTO
        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
                .username("cat4")
                .password("123QWErty!")
                .email("cat4@mail.ru")
                .phoneNumber("+375297077319")
                .build();

        User savedUser = new User(4L, "cat4", "123QWErty!", "cat4@mail.ru", "+375297077319", null, LocalDateTime.now(), null);

//        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(generalMapper.userRegistrationDTOToUser(userRegistrationDTO));
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(savedUser);

        //        When
        ResultActions resultActions = mockMvc.perform(post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(generalMapper.userRegistrationDTOToUser(userRegistrationDTO))));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.username", CoreMatchers.is(generalMapper.userRegistrationDTOToUser(userRegistrationDTO).getUsername())))
                .andExpect(jsonPath("$.id").isNumber());
    }


    @Test
    void getAll_whenGet_returnUserListWithGetDTOAndStatus201() throws Exception {

        //        Given
        Mockito.when(this.userService.findAll()).thenReturn(userList);


        //        When
        ResultActions resultActions = mockMvc.perform(get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(generalMapper.userToUserGetDTO(userList.get(0)))));

        //        Then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(generalMapper.userToUserGetDTO(userList.get(0)))))
                .andExpect(jsonPath("$.size()").value(3));

        //        Then
        Mockito.verify(userService, Mockito.times(1)).findAll();
        Mockito.verifyNoInteractions(userService);
    }


    @AfterEach
    void tearDown(){
        Mockito.reset(userService);
    }
}
