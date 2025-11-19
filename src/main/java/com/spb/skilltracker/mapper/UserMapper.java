package com.spb.skilltracker.mapper;

import com.spb.skilltracker.dto.auth.response.UserResponse;
import com.spb.skilltracker.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(UserEntity entity);

    List<UserResponse> toResponseList(List<UserEntity> entityList);
}
