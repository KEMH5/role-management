package com.corp.springsecurityasymetricencryption.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id %s", NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Current password and new password are not the same", BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "Current password is invalid", BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED", "Account already deactivated", BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED("ACCOUNT_ALREADY_ACTIVATED", "Account already activated", BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already exited", BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS", "Phone number already exists", BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password don't match", BAD_REQUEST)
    ;

    private final String code;
    private final String defaultMessage;
    // i18n / l10n
    private final HttpStatus status;

    ErrorCode(
            final String code,
            final String defaultMessage,
            final HttpStatus status
    ){
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;

    }
}
