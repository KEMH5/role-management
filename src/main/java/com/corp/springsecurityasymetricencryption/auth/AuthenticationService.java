package com.corp.springsecurityasymetricencryption.auth;

import com.corp.springsecurityasymetricencryption.auth.request.AuthenticationRequest;
import com.corp.springsecurityasymetricencryption.auth.request.RefreshRequest;
import com.corp.springsecurityasymetricencryption.auth.request.RegistrationRequest;
import com.corp.springsecurityasymetricencryption.auth.response.AuthenticationResponse;
import org.springframework.security.web.webauthn.management.RelyingPartyAuthenticationRequest;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);


}
