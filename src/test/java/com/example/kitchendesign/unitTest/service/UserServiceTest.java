package com.example.kitchendesign.unitTest.service;

import com.example.kitchendesign.dto.userDTO.UserUpdateDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.exception.NotFoundException;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.mapper.GeneralMapperImpl;
import com.example.kitchendesign.repository.UserRepository;
import com.example.kitchendesign.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final GeneralMapper generalMapper = new GeneralMapperImpl();


    @Test
    void save_withValidData_saveToDatabase() {

        //        Given
        User newUser = new User(null, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, null, null);
        //        newUser after saving in data base
        User savedUser = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.now(), null);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));

        //        When
        User savedUser1 = userService.save(newUser);

        //        Then
        Assertions.assertNotNull(savedUser1, "User was saved successfully!");
        Assertions.assertEquals(savedUser, savedUser1);
        Assertions.assertEquals(savedUser1, userRepository.findById(1L).get());
        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);
    }


    @Test
    void save_withValidDataIfUserIsPresent_returnException() {
        User existedUser = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2ForSaving = new User(2L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);

        Mockito.when(userService.save(user2ForSaving))
                .thenThrow(new NotFoundException(HttpStatusCode.valueOf(409), "Account with this username is already exist"))
                .thenThrow(new NotFoundException(HttpStatusCode.valueOf(409), "Account with this email is already exist"))
                .thenThrow(new NotFoundException(HttpStatusCode.valueOf(409), "Account with this phone number is already exist"));
    }


    @Test
    void isUsernameAlreadyUsed_ifItUsed_ReturnTrue() {

        //        Given
        User exsistedUser = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.now(), null);
        User newUser = new User(2L, "cat1", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.now(), null);
//        Mockito.when(userRepository.findByUsername(newUser.getUsername()).get().getUsername().equals(exsistedUser.getUsername())).thenReturn(true);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(exsistedUser);
        Mockito.when(userRepository.findByUsername(newUser.getUsername()).isPresent()).thenReturn(true);

        //        When
        User userByUsername = userService.findByUsername(newUser.getUsername());

        //        Then
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(newUser.getUsername());
        Mockito.verify(userService, Mockito.times(1)).isUsernameAlreadyUsed(newUser.getUsername());
    }


    @Test
    void findById_ifExisted_returnUser() {

        //        Given
        Long userId = 1L;
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        //        When
        User userFindById = userService.findById(userId);

        //        Then
        Assertions.assertEquals(userFindById, user1);
        Assertions.assertNotNull(userFindById, "This user is exist!");
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }


    @Test
    void findById_ifNotExisted_returnException() {

        //        Given
        Long userId = 2L;
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //        When
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.findById(userId));

        //        Then
//        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findById(userId), "404 User not found");
        Assertions.assertEquals("404 User not found", notFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }


    @Test
    void findByUsername_ifExisted_returnUser() {

        //        Given
        String username = "cat1";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));

        //        When
        User userByUsername = userService.findByUsername(username);

        //        Then
        Assertions.assertEquals(userByUsername, user1);
        Assertions.assertNotNull(userByUsername, "This user is exist!");
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
    }


    @Test
    void findByUsername_ifNotExisted_returnException() {

        //        Given
        String username = "cat2";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //        When
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.findByUsername(username));

        //        Then
        Assertions.assertEquals("404 User not found", notFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
    }


    @Test
    void findByEmail_ifExisted_returnUser() {

        //        Given
        String email = "cat1@mail.ru";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user1));

        //        When
        User userByEmail = userService.findByEmail(email);

        //        Then
        Assertions.assertEquals(userByEmail, user1);
        Assertions.assertNotNull(userByEmail, "This user is exist!");
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }


    @Test
    void findByEmail_ifNotExisted_returnException() {

        //        Given
        String email = "cat2@mail.ru";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //        When
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.findByEmail(email));

        //        Then
        Assertions.assertEquals("404 User not found", notFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }


    @Test
    void findByPhoneNumber_ifExisted_returnUser() {

        //        Given
        String phoneNumber = "+375297077317";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user1));

        //        When
        User userByPhoneNumber = userService.findByPhoneNumber(phoneNumber);

        //        Then
        Assertions.assertEquals(userByPhoneNumber, user1);
        Assertions.assertNotNull(userByPhoneNumber);
        Mockito.verify(userRepository, Mockito.times(1)).findByPhoneNumber(phoneNumber);
    }


    @Test
    void findByPhoneNumber_ifNotExisted_returnException() {

        //        Given
        String phoneNumber = "+375297077318";
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        //        When
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.findByPhoneNumber(phoneNumber));

        //        Then
        Assertions.assertEquals("404 User not found", notFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    void updateById_ifFieldsChanged_returnSameUserWithSameId() {

        //        Given
        Long userId = 1L;
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);

        //        Updated parameters
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .username("cat2")
                .email("cat2@mail.ru")
                .password("123QWErty?")
                .phoneNumber("+375297077318")
                .build();

        BeanUtils.copyProperties(userUpdateDTO, user1);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);

        //        When
        User userFromUpdateDTO = generalMapper.userUpdateDTOToUser(userUpdateDTO);
        User updatedUser = userService.updateById(userId, userUpdateDTO);

        //        Then
//        Assertions.assertEquals(user1.getRegDate(), updatedUser.getRegDate());
//        Assertions.assertEquals(user1.getId(), updatedUser.getId());
//        Assertions.assertEquals(user1, userService.findByUsername("cat2"));
        Assertions.assertNotNull(updatedUser);
        Assertions.assertNotNull(userFromUpdateDTO);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).save(updatedUser);
    }


    @Test
    void findAll_ifExist_getList() {

        //        Given
        User user1 = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        User user2 = new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        User user3 = new User(3L, "cat3", "123QWErty!", "cat3@mail.ru", "+375297077319", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));

        //        When
        List<User> userList = userService.findAll();

        //        Then
        Assertions.assertEquals(3, userList.size());
        Assertions.assertEquals("cat1", userList.get(0).getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }


    @Test
    void removeById_ifExist_removeUserAndReturnUser() {

        //        Given
        Long userId = 1L;
        User userBeforeDeleting = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(userBeforeDeleting));

        //        When
        User userAfterDeleting = userService.removeById(userBeforeDeleting.getId());

        //        Then
        Assertions.assertEquals(1L, userAfterDeleting.getId());
        Assertions.assertEquals(userAfterDeleting, userBeforeDeleting);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }


    @Test
    void removeById_ifNotExist_returnException() {

        //        Given
        Long userId = 2L;
        User userBeforeDeleting = new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //        When
        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.removeById(userId));

        //        Then
        Assertions.assertEquals("404 User not found", notFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }


    @AfterEach
    void tearDown() {
        Mockito.reset(userRepository);
    }
}
