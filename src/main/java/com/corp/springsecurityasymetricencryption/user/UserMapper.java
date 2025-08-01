package com.corp.springsecurityasymetricencryption.user;

import com.corp.springsecurityasymetricencryption.auth.request.RegistrationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.corp.springsecurityasymetricencryption.user.request.ProfileUpdateRequest;

@Service
public class UserMapper {

    public void mergeUserInfo(final User savedUser, final ProfileUpdateRequest request){
        if(StringUtils.isNotBlank(request.getFirstName())
            && !savedUser.getFirstName().equals(request.getFirstName()))
        {
            savedUser.setFirstName(request.getFirstName());
        }
        if(StringUtils.isNotBlank(request.getLastName())
        && !savedUser.getLastName().equals(request.getLastName())){
            savedUser.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null
        && !savedUser.getDateOfBirth().equals(request.getDateOfBirth())
            && !request.getDateOfBirth().equals(savedUser.getDateOfBirth()))
        {
            savedUser.setDateOfBirth(request.getDateOfBirth());
        }
    }

    public User toUser(final RegistrationRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber((request.getPhoneNumber()))
                .password(request.getPassword())
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .phoneVerified(false)
                .emailVerified(false)
                .build();
    }
}
