package com.caring.user_service.domain.user.mapper;

import com.caring.user_service.domain.user.entity.User;
import com.caring.user_service.domain.user.vo.request.ResponseUser;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ResponseUser toResponseUserVo(User user);

}
