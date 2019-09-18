package com.backend.helpdesk.converters.ProfileConverter;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToProfile extends Converter<UserEntity, Profile> {
    @Override
    public Profile convert(UserEntity source) {
        Profile profile = new Profile();
        profile.setId(source.getId());
        profile.setAddress(source.getAddress());
        profile.setAge(source.getAge());
        profile.setBirthday(source.getBirthday());
        profile.setEmail(source.getEmail());
        profile.setFirstName(source.getFirstName());
        profile.setLastName(source.getLastName());
        profile.setSex(source.isSex());
        profile.setStartingDay(source.getStartingDay());
        return profile;
    }
}
