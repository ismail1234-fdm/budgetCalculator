package com.budgetCalculator.mappers;

import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.response.UserRequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestMapper {

    RequestMapper MAPPER  = Mappers.getMapper(RequestMapper.class);

    @Mapping(target="responseName", source="username")
    UserRequestResponse toUSerRequestResponse(AppUser user);

}
