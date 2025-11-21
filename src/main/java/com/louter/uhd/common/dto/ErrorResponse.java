package com.louter.uhd.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    @NotBlank(message = "코드는 필수 반환값입니다.")
    private String code;
    @NotBlank(message = "메세지는 필수 반환값입니다.")
    private String message;
    @NotNull(message = "타임스탬프는 필수 반환값입니다.")
    private LocalDateTime timestamp;
    @NotNull(message = "상세 정보는 필수 반환값입니다.")
    private Map<String, String> details;

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(String code, String message, Map<String, String> details) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();
    }
}
