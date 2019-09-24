package com.backend.helpdesk.converters.ProfileConverter;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.SkillsEntity;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class UserEntityToProfile extends Converter<UserEntity, Profile> {

    @Autowired
    private Converter<SkillsEntity, Skills> skillsEntityToSkills;

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

        List<Skills> listSkills = new ArrayList<>();
        for (SkillsEntity skillsEntity : source.getSkillsEntities()) {
            listSkills.add(skillsEntityToSkills.convert(skillsEntity));
        }
        profile.setSkills(listSkills);

        // client read image base 64
        if(source.getAvatar() != null){
            profile.setAvatarBase64(new String(Base64.getEncoder().encode(source.getAvatar())));
        }
        profile.setEnable(source.isEnable());

        return profile;
    }
}
