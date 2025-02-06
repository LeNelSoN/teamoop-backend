package com.edj.teamoop.mapper;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
