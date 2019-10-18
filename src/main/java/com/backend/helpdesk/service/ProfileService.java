package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.EmailUserIsNotMatch;
import com.backend.helpdesk.exception.UserException.UserNotFoundException;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private Converter<UserEntity, Profile> userEntityToProfile;

    @Autowired
    private Converter<Profile, UserEntity> profileToUserEntity;

    public Profile getProfile(String emailUser) {

        if (userRepository.findByEmail(emailUser) == null) {
            throw new UserNotFoundException();
        }

        return userEntityToProfile.convert(userRepository.findByEmail(emailUser).get());
    }

    public void editProfile(Profile profile) {

        Optional<UserEntity> userEntityOpt = userRepository.findById(profile.getId());
        if (!userEntityOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userEntityOpt.get();

        // check for email from client is exactly
        if (!userEntity.getEmail().equals(profile.getEmail())) {
            throw new EmailUserIsNotMatch();
        }
        UserEntity resultUserEntity = profileToUserEntity.convert(profile);

        // add password default
        resultUserEntity.setPassword(userEntity.getPassword());

        // add avatar default
        resultUserEntity.setAvatar(userEntity.getAvatar());

        // add role default
        resultUserEntity.setRoleEntities(userEntity.getRoleEntities());

        userRepository.save(resultUserEntity);
    }

    public void uploadAvatar(byte[] avatar) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserEntity userEntity = userRepository.findByEmail(currentPrincipalName).get();
        userEntity.setAvatar(avatar);

        userRepository.save(userEntity);
    }

    public Profile getProfileFollowIdUser(int idUser) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(idUser);
        if (userEntityOpt.isPresent()) {
            return userEntityToProfile.convert(userEntityOpt.get());
        }
        throw new UserNotFoundException();
    }

    public List<Profile> searchAllUserFollowKeyWord(String keyword) {
        List<Profile> profiles = new ArrayList<>();

        for (UserEntity userEntity : userRepository.findAllUserByKeyword(keyword)) {
            profiles.add(userEntityToProfile.convert(userEntity));
        }
        return profiles;
    }
}

