package com.corp.springsecurityasymetricencryption.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "VALIDATION.CHANGE.CURRENT_PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 72,
            message = "VALIDATION.CHANGE.CURRENT_PASSWORD.SIZE"
    )
    @Schema(example = "<PASSWORD>")
    private String currentPassword;

    @NotBlank(message = "VALIDATION.CHANGE.NEW_PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 72,
            message = "VALIDATION.CHANGE.NEW_PASSWORD.SIZE"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).*$",
            message = "VALIDATION.CHANGE.NEW_PASSWORD.WEAK"
    )
    @Schema(example = "<PASSWORD>")
    private String newPassword;
    @NotBlank(message = "VALIDATION.CHANGE.CONFIRM_PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 72,
            message = "VALIDATION.CHANGE.CONFIRM_PASSWORD.SIZE"
    )
    @Schema(example = "<PASSWORD>")
    private String confirmNewPassword;
}
