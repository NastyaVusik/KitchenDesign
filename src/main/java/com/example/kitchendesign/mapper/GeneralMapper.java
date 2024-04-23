package com.example.kitchendesign.mapper;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import com.example.kitchendesign.dto.userDTO.*;
import com.example.kitchendesign.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface GeneralMapper {

    GeneralMapper MAPPER = Mappers.getMapper(GeneralMapper.class);

    User userPostDTOToUser(UserPostDTO userPostDTO);

    User userRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO);

    UserGetDTO userToUserGetDTO(User user);

    User userLoginDTOToUser(UserLoginDTO userLoginDTO);

    User userUpdateDTOToUser(UserUpdateDTO userUpdateDTO);

    SimpleEmailDTO UserToSimpleEmailDTO(User user);
}
