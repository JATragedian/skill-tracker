package com.spb.skilltracker.mapper;

import com.spb.skilltracker.dto.skillassignment.response.SkillAssignmentResponse;
import com.spb.skilltracker.entity.SkillAssignmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkillAssignmentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "skillId", source = "skill.id")
    SkillAssignmentResponse toResponse(SkillAssignmentEntity entity);

    List<SkillAssignmentResponse> toResponseList(List<SkillAssignmentEntity> entityList);
}
