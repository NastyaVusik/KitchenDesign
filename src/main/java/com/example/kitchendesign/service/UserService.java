package com.example.kitchendesign.service;

import com.example.kitchendesign.dto.userDTO.UserUpdateDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GeneralMapper generalMapper;


    public User save(User user) {

        user.setRegDate(LocalDateTime.now());
//        if (!user.getPhoneNumber().equals(userRepository.findByPhoneNumber(user.getPhoneNumber()).get()) |
//            !user.getEmail().equals(userRepository.findByPhoneNumber(user.getEmail()).get()) |
//            !user.getPhoneNumber().equals(userRepository.findByPhoneNumber(user.getPhoneNumber()).get())) {
//            throw new RuntimeException("User with this data is already exist");
//        }

        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public boolean isUsernameAlreadyUsed(String username) {

        boolean isUsernameOccupied = false;

        if (userRepository.findByUsername(username).isPresent()) {
            isUsernameOccupied = true;
        }

        return isUsernameOccupied;
    }


    @Transactional(readOnly = true)
    public boolean isEmailAlreadyUsed(String email) {

        boolean isEmailOccupied = false;

        if (userRepository.findByEmail(email).isPresent()) {
            isEmailOccupied = true;
        }

        return isEmailOccupied;
    }


    @Transactional(readOnly = true)
    public boolean isPhoneNumberAlreadyUsed(String phoneNumber) {

        boolean isPhoneNumberOccupied = false;

        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            isPhoneNumberOccupied = true;
        }

        return isPhoneNumberOccupied;
    }


    @Transactional(readOnly = true)
    public User findById(Long id) {

        Optional<User> userById = Optional.of(userRepository.findById(id).orElseThrow());           //?????????????????????

        return userById.get();
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        Optional<User> userByUsername = Optional.of(userRepository.findByUsername(username).orElseThrow());

        return userByUsername.get();
    }


    @Transactional(readOnly = true)
    public User findByEmail(String email) {

        Optional<User> userByEmail = Optional.of(userRepository.findByEmail(email).orElseThrow());

        return userByEmail.get();
    }


    @Transactional(readOnly = true)
    public User findByPhoneNumber(String phoneNumber) {

        Optional<User> userByPhoneNumber = Optional.of(userRepository.findByPhoneNumber(phoneNumber).orElseThrow());

        return userByPhoneNumber.get();
    }

    public User updateById(Long id, UserUpdateDTO userUpdateDTO) {

        Optional<User> userById = Optional.of(userRepository.findById(id).orElseThrow());

        User user = userById.get();
        user.setUsername(generalMapper.userUpdateDTOToUser(userUpdateDTO).getUsername());
        user.setPassword(generalMapper.userUpdateDTOToUser(userUpdateDTO).getPassword());
        user.setEmail(generalMapper.userUpdateDTOToUser(userUpdateDTO).getEmail());
        user.setPhoneNumber(generalMapper.userUpdateDTOToUser(userUpdateDTO).getPhoneNumber());

        return user;
    }


    @Transactional(readOnly = true)
    public List<User> findAll() {

        return userRepository.findAll();
    }


    public User removeById(Long id) {

        Optional<User> userById = Optional.of(userRepository.findById(id).orElseThrow());

        User user = userById.get();
        userRepository.deleteById(id);

        return user;
    }

}
