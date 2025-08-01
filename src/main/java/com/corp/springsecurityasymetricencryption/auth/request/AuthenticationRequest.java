package com.corp.springsecurityasymetricencryption.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL_NOT_BLANK")
    @Email(message = "VALIDATION.AUTHENTICATION.EMAIL.FORMAT")
    @Schema(example = "ali@mail.com")
    private String email;
    @NotBlank(message = "VALIDATION.AUTHENTICATION.PASSWORD_NOT_BLANK")
    @Schema(example = "<PASSWORD>")
    private String password;
}
