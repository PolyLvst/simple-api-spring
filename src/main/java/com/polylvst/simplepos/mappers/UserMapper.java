package com.polylvst.simplepos.mappers;

import com.polylvst.simplepos.domain.dtos.CreateUserRequest;
import com.polylvst.simplepos.domain.dtos.UserDto;
import com.polylvst.simplepos.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE) // Jika tidak dapat di map hiraukan saja
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(CreateUserRequest createUserRequest);
}
