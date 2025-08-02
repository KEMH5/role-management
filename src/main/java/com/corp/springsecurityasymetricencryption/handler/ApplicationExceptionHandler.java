package com.corp.springsecurityasymetricencryption.handler;

import com.corp.springsecurityasymetricencryption.user.exception.BusinessException;
import com.corp.springsecurityasymetricencryption.user.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.corp.springsecurityasymetricencryption.user.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException ex){
        final ErrorResponse body = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .build();

        log.error("Business exception: {}", ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return ResponseEntity.status(ex.getErrorCode()
                .getStatus() != null ? ex.getErrorCode()
                .getStatus() : BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)//When an account is disabled
    public ResponseEntity<ErrorResponse> handleException(final DisabledException ex){
        final ErrorResponse body = ErrorResponse.builder()
                .code(ERROR_USER_DISABLED.getCode())
                .message(ERROR_USER_DISABLED.getDefaultMessage())
                .build();
        return ResponseEntity.status(ERROR_USER_DISABLED.getStatus())
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(final BadCredentialsException exp){
        log.debug(exp.getMessage(), exp);
        final ErrorResponse response = ErrorResponse.builder()
                .message(BAD_CREDENTIALS.getDefaultMessage())
                .code(BAD_CREDENTIALS.getCode())
                .build();
        return ResponseEntity.status(BAD_CREDENTIALS.getStatus())
                .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final UsernameNotFoundException exp){
        log.debug(exp.getMessage(), exp);
        final ErrorResponse response = ErrorResponse.builder()
                .message(USERNAME_NOT_FOUND.getDefaultMessage())
                .code(USERNAME_NOT_FOUND.getCode())
                .build();
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exp){
        log.debug(exp.getMessage(), exp);
        final ErrorResponse response = ErrorResponse.builder()
                .message(INTERNAL_EXCEPTION.getDefaultMessage())
                .code(INTERNAL_EXCEPTION.getCode())
                .build();
        return ResponseEntity.status(INTERNAL_EXCEPTION.getStatus())
                .body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final EntityNotFoundException exp){
        log.debug(exp.getMessage(), exp);
        final ErrorResponse response = ErrorResponse.builder()
                .code("TO_BE_DEFINED")
                .message(exp.getMessage())
                .build();
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(final MethodArgumentNotValidException exp){
        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        exp.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorCode = error.getDefaultMessage();
                    errors.add(ErrorResponse.ValidationError.builder()
                                    .field(fieldName)
                                    .code(errorCode)
                                    .message(errorCode)
                            .build());
                });

        final ErrorResponse response = ErrorResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(BAD_REQUEST)
                .body(response);
    }


}
