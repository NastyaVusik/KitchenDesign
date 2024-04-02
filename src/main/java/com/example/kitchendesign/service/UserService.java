package com.example.kitchendesign.service;

import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User save(User user){

        user.setRegDate(LocalDateTime.now());

        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User findById(Long id){

        Optional<User> userById = Optional.of(userRepository.findById(id).orElseThrow());           //?????????????????????

        return userById.get();
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username){

        Optional<User> userByUsername = Optional.of(userRepository.findByUsername(username).orElseThrow());

        return userByUsername.get();
    }

}
