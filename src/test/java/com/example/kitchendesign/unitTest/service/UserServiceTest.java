package com.example.kitchendesign.unitTest.service;

import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.exception.NotFoundException;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.repository.UserRepository;
import com.example.kitchendesign.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GeneralMapper generalMapper;

    @InjectMocks
    private UserService userService;


    @Test
    void save_withValidData_saveToDatabase() {

        User savedUser = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User expectedeUser = userService.findById(savedUser.getId());

        Assertions.assertNotNull(savedUser, "User was saved successfully!");
        Assertions.assertEquals(expectedeUser.getUsername(), savedUser.getUsername());
        Assertions.assertEquals(savedUser, userRepository.findById(1L).get());
        Mockito.when(userService.findById(1L)).thenReturn(savedUser);
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

        User exsistedUser = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User newUser = userService.save(new User(2L, "cat1", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        Assertions.assertEquals(userService.findByUsername(exsistedUser.getUsername()), userService.findByUsername(newUser.getUsername()));
        Assertions.assertFalse(userService.isUsernameAlreadyUsed(exsistedUser.getUsername()));
        Assertions.assertTrue(userService.isUsernameAlreadyUsed(newUser.getUsername()));
    }


    @Test
    void findById_ifExisted_returnUserElseException() {

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        Assertions.assertEquals(user1, userService.findById(1L));
        Assertions.assertNotNull(userService.findById(1L), "This user is exist!");
        Assertions.assertNotSame(user2, userService.findById(1L));
        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findById(3L), "User not found");
        Mockito.when(userService.findById(1L).equals(user1)).thenReturn(true);
        Mockito.verify(1);
    }


    @Test
    void findByUsername_ifExisted_returnUserElseException() {

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findByUsername("cat3"), "User not found");
        Mockito.when(userService.findByUsername("cat1").equals(user1)).thenReturn(true);
        Mockito.verify(1);
    }


    @Test
    void findByEmail_ifExisted_returnUserElseException() {

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findByEmail("cat3@mail.ru"), "User not found");
        Mockito.when(userService.findByEmail("cat1@mail.ru").equals(user1)).thenReturn(true);
        Mockito.verify(1);
    }


    @Test
    void findByPhoneNumber_ifExisted_returnUserElseException() {

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findByPhoneNumber("+375297077319"), "User not found");
        Mockito.when(userService.findByPhoneNumber("+375297077317").equals(user1)).thenReturn(true);
        Mockito.verify(1);
    }


    @Test
    void updateById_ifFieldsChanged_returnSameUserElseException() {

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        user1.setUsername("cat2");
        user1.setEmail("cat2@mail.ru");
        user1.setEmail("123QWErty?");
        user1.setPhoneNumber("+375297077318");

        Assertions.assertThrows(NotFoundException.class, (Executable) userService.findById(2L), "User not found");
        Assertions.assertTrue(userService.findByUsername("cat2").getRegDate().isEqual(user1.getRegDate()));
        Mockito.when(userService.findByUsername("cat2").equals(user1)).thenReturn(true);
        Mockito.when(userService.findByEmail("cat2@mail.ru").equals(user1)).thenReturn(true);
        Mockito.when(userService.findByPhoneNumber("+375297077318").equals(user1)).thenReturn(true);
        Mockito.when(userService.findByUsername("cat2").equals(userService.findById(1L))).thenReturn(true);
        Mockito.when(userService.findByEmail("cat2@mail.ru").equals(userService.findById(1L))).thenReturn(true);
        Mockito.when(userService.findByUsername("+375297077318").equals(userService.findById(1L))).thenReturn(true);
        Mockito.verify(1);
    }


    @Test
    void findAll_ifExist_getList(){

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        User user3 = userService.save(new User(3L, "cat3", "123QWErty!", "cat3@mail.ru", "+375297077319", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));

        List<User> userList = userService.findAll();

        Assertions.assertEquals(3, userList.size());
        Assertions.assertEquals("cat1", userList.get(0).getUsername());
        Mockito.verify(userService, Mockito.times(userList.size())).findAll();           //??????????????????????????????
    }


    @Test
    void removeById_ifExist_removeUserAndReturnUserElseException() {

//        Given
        User userBeforeDeleting = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31T23:59:59.0000"), null));
        Optional<User> userAfterDeleting = Optional.ofNullable(userService.findById(userBeforeDeleting.getId()));

//        When
        userService.removeById(userBeforeDeleting.getId());

//        Then
        Assertions.assertEquals(1L, userService.removeById(userBeforeDeleting.getId()).getId());
        Assertions.assertTrue(userAfterDeleting.isEmpty());
        Assertions.assertThrows(NotFoundException.class, (Executable) userService.removeById(2L), "User not found");
        Mockito.when(userService.removeById(userBeforeDeleting.getId())).thenReturn(userBeforeDeleting);
        Mockito.verify(1);
    }
}
