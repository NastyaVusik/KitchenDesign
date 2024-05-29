package com.example.kitchendesign.unitTest.web.controller;

import com.example.kitchendesign.dto.userDTO.UserGetDTO;
import com.example.kitchendesign.dto.userDTO.UserLoginDTO;
import com.example.kitchendesign.dto.userDTO.UserRegistrationDTO;
import com.example.kitchendesign.dto.userDTO.UserUpdateDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.exception.NotFoundException;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.service.emailService.DefaultEmailService;
import com.example.kitchendesign.util.DateAndTimeFormatter;
import com.example.kitchendesign.web.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void registration_whenUserSaved_returnJsonStatus201() throws Exception {

        //        Given
        //        Parameters for userRegistrationDTO
        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
                .username("cat4")
                .password("123QWErty!")
                .email("cat4@mail.ru")
                .phoneNumber("+375297077319")
                .build();

        User savedUser = new User(4L, "cat4", "123QWErty!", "cat4@mail.ru", "+375297077319", null, LocalDateTime.now(), null);

        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(savedUser);

        //        When
        ResultActions resultActions = mockMvc.perform(post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.username").value(userRegistrationDTO.getUsername()))
                .andExpect(jsonPath("$.id").isNumber());

        Mockito.verify(userService, Mockito.times(1)).save(Mockito.any(User.class));
    }


    @Test
    void registration_ifUserDataNotValid_returnHttpClientErrorExceptionWithStatus409(){

        //        Given

        //        When


        //        Then
    }


    @Test
    void loginWithUserName_ifUsernameAndPasswordCorrect_returnUserAndStatus200() throws Exception{

        //        Given
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("cat2")
                .password("123QWErty&")
                .build();

        User userByUsername = userList.get(1);

        Mockito.when(userService.findByUsername("cat2")).thenReturn(userByUsername);


        //        When
        ResultActions resultActions = mockMvc.perform(post("/user/login/username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.email").value(userByUsername.getEmail()))
                .andExpect(jsonPath("$.id").value(userByUsername.getId()));

        Mockito.verify(userService, Mockito.times(1)).findByUsername(userLoginDTO.getUsername());
    }


    @Test
    void loginWithEmail_ifEmailAndPasswordCorrect_returnUserAndStatus200() throws Exception {

        //        Given
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("cat2@mail.ru")
                .password("123QWErty&")
                .build();

        User userByEmail = userList.get(1);

        Mockito.when(userService.findByEmail("cat2@mail.ru")).thenReturn(userByEmail);

        //        Then
        ResultActions resultActions = mockMvc.perform(post("/user/login/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value(userByEmail.getUsername()))
                .andExpect(jsonPath("$.id").value(userByEmail.getId()));

        Mockito.verify(userService, Mockito.times(1)).findByEmail(userLoginDTO.getEmail());
    }


    @Test
    void loginWithPhoneNumber_ifPhoneNumberAndPasswordCorrect_returnUserAndStatus200() throws Exception{

        //        Given
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .phoneNumber("+375297077317")
                .password("123QWErty&")
                .build();

        User userByPhoneNumber = userList.get(1);

        Mockito.when(userService.findByPhoneNumber("+375297077317")).thenReturn(userByPhoneNumber);

        //        When
        ResultActions resultActions = mockMvc.perform(post("/user/login/phoneNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value(userByPhoneNumber.getUsername()))
                .andExpect(jsonPath("$.id").value(userByPhoneNumber.getId()));

        Mockito.verify(userService, Mockito.times(1)).findByPhoneNumber(userLoginDTO.getPhoneNumber());
    }


    @Test
    void getById_ifExisted_returnUserAndStatus200() throws Exception{

        //        Given
        Long userId = 1L;
        User userById = userList.get(0);

        //        Parameters for UserGetDTO
        UserGetDTO userGetDTO = UserGetDTO.builder()
                .id(userId)
                .username(userById.getUsername())
                .email(userById.getEmail())
                .phoneNumber(userById.getPhoneNumber())
                .roles(userById.getRoles())
                .regDate(userById.getRegDate())
                .projects(userById.getProjects())
                .build();

        Mockito.when(userService.findById(userId)).thenReturn(userById);

        //        When
        ResultActions resultActions = mockMvc.perform(get("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userGetDTO)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value(userById.getUsername()))
                .andExpect(jsonPath("$.id").value(userById.getId()));
    }


    @Test
    void getById_ifNotExisted_returnNotFoundExceptionAWithStatus404() throws Exception {

        //        Given
        Long userId = 4L;
        Optional<User> userById = Optional.empty();

        Mockito.when(userService.findById(userId)).thenReturn(userById);        //??????????????
        Mockito.when(userService.findById(userId)).thenThrow(new NotFoundException(HttpStatusCode.valueOf(404), "User not found"));


        //        When
        ResultActions resultActions = mockMvc.perform(get("/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))

        //        Then
                .andExpect(status().isBadRequest());

        Mockito.verify(userService, Mockito.times(1)).findById(userId);
        Mockito.verifyNoInteractions(userService);


    }


    @Test
    void getAll_whenGet_returnUserListWithGetDTOAndStatus201() throws Exception {

        //        Given
        List<UserGetDTO> userGetDTOList = userList.stream()
                .map(generalMapper::userToUserGetDTO)
                .collect(Collectors.toList());

        Mockito.when(this.userService.findAll()).thenReturn(userList);

        //        When
        ResultActions resultActions = mockMvc.perform(get("/user/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userGetDTOList)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userGetDTOList)))
                .andExpect(jsonPath("$.size()").value(3));

        Mockito.verify(userService, Mockito.times(1)).findAll();
    }


    @Test
    void updateById_ifExisted_returnUpdatedUserAndStatus200() throws Exception {

        //        Given
        Long userId = 1L;
        User userByIdForUpdate = userList.get(0);

        //        Parameters for userUpdateDTO
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .username("Updatecat1")
                .password("Update123QWErty!")
                .email("Updatecat1@mail.ru")
                .phoneNumber("+375297077319")
                .build();

        userByIdForUpdate.setUsername(userUpdateDTO.getUsername());
        userByIdForUpdate.setPassword(userUpdateDTO.getPassword());
        userByIdForUpdate.setEmail(userUpdateDTO.getEmail());
        userByIdForUpdate.setPhoneNumber(userUpdateDTO.getPhoneNumber());

        Mockito.when(userService.findById(userId)).thenReturn(userByIdForUpdate);
        Mockito.when(userService.updateById(userId, userUpdateDTO)).thenReturn(userByIdForUpdate);

        //        When
        ResultActions resultActions = mockMvc.perform(put("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateDTO)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.regDate").value(DateAndTimeFormatter.dateAndTimeParser(userByIdForUpdate.getRegDate())))
                .andExpect(jsonPath("$.id").value(userByIdForUpdate.getId()));

        Mockito.verify(userService, Mockito.times(1)).findById(userId);
        Mockito.verify(userService, Mockito.times(1)).updateById(userId, userUpdateDTO);
    }


    @Test
    void deleteById_ifExisted_returnUpdatedUserAndStatus200() throws Exception{

        //        Given
        Long userId = 1L;
        User userByIdForDeletion = userList.get(0);

        Mockito.when(userService.findById(userId)).thenReturn(userByIdForDeletion);
        Mockito.when(userService.removeById(userId)).thenReturn(userByIdForDeletion);

        //        When
        ResultActions resultActions = mockMvc.perform(delete("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userByIdForDeletion)));

        //        Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value(userByIdForDeletion.getUsername()))
                .andExpect(jsonPath("$.id").value(userByIdForDeletion.getId()));

        Mockito.verify(userService, Mockito.times(1)).removeById(userId);
    }


    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }
}
