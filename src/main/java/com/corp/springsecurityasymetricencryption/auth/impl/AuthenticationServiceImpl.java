package com.corp.springsecurityasymetricencryption.auth.impl;

import com.corp.springsecurityasymetricencryption.auth.AuthenticationService;
import com.corp.springsecurityasymetricencryption.auth.request.AuthenticationRequest;
import com.corp.springsecurityasymetricencryption.auth.request.RefreshRequest;
import com.corp.springsecurityasymetricencryption.auth.request.RegistrationRequest;
import com.corp.springsecurityasymetricencryption.auth.response.AuthenticationResponse;
import com.corp.springsecurityasymetricencryption.role.RoleRepository;
import com.corp.springsecurityasymetricencryption.role.Role;
import com.corp.springsecurityasymetricencryption.security.JwtService;
import com.corp.springsecurityasymetricencryption.user.UserMapper;
import com.corp.springsecurityasymetricencryption.user.User;
import com.corp.springsecurityasymetricencryption.user.UserRepository;
import com.corp.springsecurityasymetricencryption.user.exception.BusinessException;
import com.corp.springsecurityasymetricencryption.user.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.webauthn.management.RelyingPartyAuthenticationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(final AuthenticationRequest request) {
        final Authentication auth = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );

        final User user = (User) auth.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());

        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();

    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        //In case you have cascading option
        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        this.roleRepository.save(userRole);
    }


    @Override
    public AuthenticationResponse refreshToken(final RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkUserEmail(String email) {
        final boolean exists = this.userRepository.existsByEmailIgnoreCase(email);
        if (exists){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);
        if (phoneNumberExists){
            throw new BusinessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if(password == null || !password.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
