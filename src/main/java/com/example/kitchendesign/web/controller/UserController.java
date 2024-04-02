package com.example.kitchendesign.web.controller;

import com.example.kitchendesign.dto.userDTO.UserGetDTO;
import com.example.kitchendesign.dto.userDTO.UserLoginDTO;
import com.example.kitchendesign.dto.userDTO.UserPostDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final GeneralMapper generalMapper;


    @PostMapping("/registration")
    public ResponseEntity<User> registration(@Valid @RequestBody UserPostDTO userPostDTO) {

        User user = userService.save(generalMapper.userPostDTOToUser(userPostDTO));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody UserLoginDTO userLoginDTO){

        User user = userService.findByUsername(generalMapper.userLoginDTOToUser(userLoginDTO).getUsername());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> getById(@PathVariable(value = "id") Long id) {

        UserGetDTO user = generalMapper.userToUserGetDTO(userService.findById(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
