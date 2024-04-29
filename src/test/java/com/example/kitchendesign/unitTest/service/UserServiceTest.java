package com.example.kitchendesign.unitTest.service;

import com.example.kitchendesign.dto.userDTO.UserGetDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.exception.NotFoundException;
import com.example.kitchendesign.repository.UserRepository;
import com.example.kitchendesign.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void save_withValidData_saveToDatabase() {

        User savedUser = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));
        User expectedeUser = userService.findById(savedUser.getId());

        Assertions.assertNotNull(savedUser, "User was saved successfully!");
        Assertions.assertEquals(expectedeUser.getUsername(), savedUser.getUsername());
        Assertions.assertEquals(savedUser, userRepository.findById(1L).get());
        Mockito.when(userService.findById(1L)).thenReturn(savedUser);
    }


    @Test
    void isUsernameAlreadyUsed_ifItUsed_ReturnTrue() {

        User exsistedUser = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));
        User newUser = userService.save(new User(2L, "cat1", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));

        Assertions.assertEquals(userService.findByUsername(exsistedUser.getUsername()), userService.findByUsername(newUser.getUsername()));
        Assertions.assertFalse(userService.isUsernameAlreadyUsed(exsistedUser.getUsername()));
        Assertions.assertTrue(userService.isUsernameAlreadyUsed(newUser.getUsername()));
    }


    @Test
    void findById_ifExisted_ReturnUserOrException(){

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));

        Assertions.assertEquals(user1, userService.findById(1L));
        Assertions.assertNotNull(userService.findById(1L), "This user is exist!");
        Assertions.assertNotSame(user2, userService.findById(1L));
        Assertions.assertThrowsExactly(NotFoundException.class, (Executable) userService.findById(3L), "User not found");
    }


    @Test
    void findAll_ifExist_getList(){

        User user1 = userService.save(new User(1L, "cat1", "123QWErty!", "cat1@mail.ru", "+375297077317", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));
        User user2 = userService.save(new User(2L, "cat2", "123QWErty!", "cat2@mail.ru", "+375297077318", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));
        User user3 = userService.save(new User(3L, "cat3", "123QWErty!", "cat3@mail.ru", "+375297077319", null, LocalDateTime.parse("2019-12-31 23:59:590000"), null));

        List<User> userList = userService.findAll();

        Assertions.assertEquals(3, userList.size());
        Assertions.assertEquals("cat1", userList.get(0).getUsername());
    }


}
