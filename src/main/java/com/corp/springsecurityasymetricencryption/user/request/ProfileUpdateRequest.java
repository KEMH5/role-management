package com.corp.springsecurityasymetricencryption.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {
    @NotBlank(message = "VALIDATION.UPDATE.FIRSTNAME.NOT_BLANK")
    @Size(
            min = 5,
            max = 50,
            message = "VALIDATION.UPDATE.FIRSTNAME.Size"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",//Firstname should start with UpperCaseLetter
            message = "VALIDATION.UPDATE.FIRSTNAME.PATTERN"
    )
    @Schema(example = "Ali")
    private String firstName;

    @NotBlank(message = "VALIDATION.UPDATE.LASTNAME.NOT_BLANK")
    @Size(
            min = 5,
            max = 50,
            message = "VALIDATION.UPDATE.LASTNAME.Size"
    )
    @Pattern(
            regexp = "^[\\p{L} '-]+$",//Firstname should start with UpperCaseLetter
            message = "VALIDATION.UPDATE.LASTNAME.PATTERN"
    )
    @Schema(example = "Ali")
    private String lastName;

    @NotBlank(message = "VALIDATION.UPDATE.DATEOFBIRTH.NOT_BLANK")
    @JsonFormat(pattern = "MM/DD/YYYY")
    private LocalDate dateOfBirth;
}
