package com.example.kitchendesign.web.controller;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import com.example.kitchendesign.dto.userDTO.*;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.service.UserService;
import com.example.kitchendesign.service.emailService.DefaultEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final GeneralMapper generalMapper;
    private final DefaultEmailService defaultEmailService;


    @PostMapping("/registration")
    public ResponseEntity<User> registration(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {

        User user = userService.save(generalMapper.userRegistrationDTOToUser(userRegistrationDTO));

        defaultEmailService.sendSimpleEmail(user);

//            defaultEmailService.sendSimpleEmail(generalMapper.UserToSimpleEmailDTO(user));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PostMapping("/login/username")
    public ResponseEntity<User> loginWithUserName(@Validated(User.LoginWithUserName.class)
                                      @RequestBody UserLoginDTO userLoginDTO) {

        User user = userService.findByUsername(generalMapper.userLoginDTOToUser(userLoginDTO).getUsername());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/login/email")
    public ResponseEntity<User> loginWithEmail(@Validated(User.LoginWithEmail.class)
                                               @RequestBody UserLoginDTO userLoginDTO) {

        User user = userService.findByEmail(generalMapper.userLoginDTOToUser(userLoginDTO).getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/login/phoneNumber")
    public ResponseEntity<User> loginWithPhoneNumber(@Validated(User.LoginWithPhoneNumber.class)
                                                     @RequestBody UserLoginDTO userLoginDTO) {

        User user = userService.findByPhoneNumber(generalMapper.userLoginDTOToUser(userLoginDTO).getPhoneNumber());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> getById(@PathVariable(value = "id") Long id) {

        UserGetDTO user = generalMapper.userToUserGetDTO(userService.findById(id));

        if (user.getId().equals(id)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserGetDTO>> getAll() {

        List<UserGetDTO> userGetDTOList = userService.findAll().stream()
                .map(generalMapper::userToUserGetDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userGetDTOList, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@PathVariable(name = "id") Long id, @RequestBody UserUpdateDTO userUpdateDTO) {

        User user = userService.findById(id);

        if (user.getId().equals(id)) {
            userService.updateById(id, userUpdateDTO);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable(name = "id") Long id) {

        User userById = userService.findById(id);

        if (userById.getId().equals(id)) {
            userService.removeById(id);
            return ResponseEntity.ok(userById);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
