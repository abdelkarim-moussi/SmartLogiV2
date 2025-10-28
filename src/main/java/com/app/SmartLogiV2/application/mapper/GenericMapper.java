package com.app.SmartLogiV2.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenericMapper<Entity, DTO> {

    Entity toEntity(DTO dto);
    DTO fromEntity(Entity entity);

}
