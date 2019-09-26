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

    public List<Profile> getListItem(int sizeList, int indexPage, String valueSearch, String valueSort) {
        Pageable sortedPage = getPageableSort(valueSort, indexPage, sizeList);
        return userEntityToProfile.convert(userRepository.findAllUserByKeywordFollowPageable(valueSearch, sortedPage));
    }

    public Pageable getPageableSort(String valueSort, int indexPage, int sizeList) {
        if (valueSort.equals("") || valueSort == null) {
            valueSort = "id";
        }
        return PageRequest.of(indexPage, sizeList, Sort.by(valueSort));
    }
}
