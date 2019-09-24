package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Profile;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.UserNotFoundException;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.backend.helpdesk.common.Constants.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    private Converter<UserEntity, Profile> userEntityToProfile;

    public List<Profile> getAllUser() {
        return userEntityToProfile.convert(userRepository.findAll());
    }

    public Profile getUserFollowId(int idUser) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(idUser);
        if (userEntityOpt.isPresent()) {
            return userEntityToProfile.convert(userEntityOpt.get());
        }
        throw new UserNotFoundException();
    }

    public void editUser(Profile profile) {
        profileService.editProfile(profile);
    }

    public void setStatusEnableOfUser(int idUser, boolean status) {
        Optional<UserEntity> userEntityOpt = userRepository.findById(idUser);
        if (!userEntityOpt.isPresent()) {
            throw new UserNotFoundException();
        }
        UserEntity userEntity = userEntityOpt.get();
        userEntity.setEnable(status);
        userRepository.save(userEntity);
    }

    public List<Profile> getListItem(int sizeList, int indexPage, String valueSearch, int keySort) {
        Pageable sortedPage = getPageableSort(keySort, indexPage, sizeList);
        return userEntityToProfile.convert(userRepository.findAllUserByKeywordFollowPageable(valueSearch, sortedPage));
    }

    public Pageable getPageableSort(int keySort, int indexPage, int sizeList) {
        if (keySort == SORT_BY_EMAIL) {
            return PageRequest.of(indexPage, sizeList, Sort.by("email"));
        } else if (keySort == SORT_BY_AGE) {
            return PageRequest.of(indexPage, sizeList, Sort.by("age"));
        } else if (keySort == SORT_BY_STARTING_DAY) {
            return PageRequest.of(indexPage, sizeList, Sort.by("startingDay"));
        } else if (keySort == SORT_BY_FIRST_NAME) {
            return PageRequest.of(indexPage, sizeList, Sort.by("firstName"));
        } else if (keySort == SORT_BY_LAST_NAME) {
            return PageRequest.of(indexPage, sizeList, Sort.by("lastName"));
        } else {
            return PageRequest.of(indexPage, sizeList, Sort.by("id"));
        }
    }
}
