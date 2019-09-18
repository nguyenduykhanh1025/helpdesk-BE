package com.backend.helpdesk.converters.ProfileConverter;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileToUserEntity extends Converter<Profile, UserEntity> {

    @Override
    public UserEntity convert(Profile source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(source.getId());
        userEntity.setEmail(source.getEmail());
        userEntity.setAddress(source.getAddress());
        userEntity.setAge(source.getAge());
        userEntity.setBirthday(source.getBirthday());
        userEntity.setEmail(source.getEmail());
        userEntity.setFirstName(source.getFirstName());
        userEntity.setLastName(source.getLastName());
        userEntity.setSex(source.isSex());
        userEntity.setStartingDay(source.getStartingDay());
        return userEntity;
    }
}
