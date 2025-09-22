package com.example.slothlang_be.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
// Dinh nghia kieu du lieu tra ve khi loi
public class ErrorResponse {

    String filed;

//    Code error
    String code;

//    Message Error
    String message;
}
