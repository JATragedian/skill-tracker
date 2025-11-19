package com.spb.skilltracker.mapper;

import com.spb.skilltracker.dto.skill.response.SkillResponse;
import com.spb.skilltracker.entity.SkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkillMapper {

    @Mapping(target = "category", source = "category.name")
    SkillResponse toResponse(SkillEntity entity);

    List<SkillResponse> toResponseList(List<SkillEntity> entityList);
}
