package com.corp.springsecurityasymetricencryption.auth.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    //validation later
    private String email;
    private String password;
}
