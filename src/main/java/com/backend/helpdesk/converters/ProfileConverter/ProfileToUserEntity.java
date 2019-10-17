package com.backend.helpdesk.converters.ProfileConverter;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.DTO.Skills;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.SkillsEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.SkillsRepository;
import com.backend.helpdesk.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProfileToUserEntity extends Converter<Profile, UserEntity> {

    @Autowired
    private Converter<Skills, SkillsEntity> skillsToSkillsEntity;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    SkillsService skillsService;

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
        userEntity.setSex(source.getSex());
        userEntity.setStartingDay(source.getStartingDay());

        if (source.getSkills() != null) {
            Set<SkillsEntity> listSkills = new HashSet<>();
            for (Skills skills : source.getSkills()) {

                Optional<SkillsEntity> skillsEntityOptFollowID = skillsRepository.findById(skills.getId());
                Optional<SkillsEntity> skillsEntityOptFollowName = skillsRepository.findByName(skills.getName());

                // client check for skill is edit or create new skill or not
                if (skillsEntityOptFollowID.isPresent() && !skillsEntityOptFollowName.isPresent()) {
                    skillsService.editSkills(skills);
                } else if (!skillsEntityOptFollowID.isPresent() && !skillsEntityOptFollowName.isPresent()) {
                    skillsService.addNewSkills(skills);
                }
                SkillsEntity skillsEntity = skillsRepository.findByName(skills.getName()).get();

                listSkills.add(skillsEntity);
            }
            userEntity.setSkillsEntities(listSkills);
        }


        return userEntity;
    }
}
