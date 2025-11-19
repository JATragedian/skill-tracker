package com.spb.skilltracker.mapper;

import com.spb.skilltracker.dto.category.response.CategoryResponse;
import com.spb.skilltracker.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity entity);

    List<CategoryResponse> toResponseList(List<CategoryEntity> entityList);
}
