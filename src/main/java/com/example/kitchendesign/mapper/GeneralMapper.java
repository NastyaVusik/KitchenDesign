package com.example.kitchendesign.mapper;

import com.example.kitchendesign.dto.userDTO.UserGetDTO;
import com.example.kitchendesign.dto.userDTO.UserLoginDTO;
import com.example.kitchendesign.dto.userDTO.UserPostDTO;
import com.example.kitchendesign.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface GeneralMapper {

    User userPostDTOToUser(UserPostDTO userPostDTO);

    UserGetDTO userToUserGetDTO(User user);

    User userLoginDTOToUser(UserLoginDTO userLoginDTO);
}
