package com.example.slothlang_be.dto.request.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthGoogleRequest {

    @NotBlank(message = "NOT_BLANK")
    String code;
}
