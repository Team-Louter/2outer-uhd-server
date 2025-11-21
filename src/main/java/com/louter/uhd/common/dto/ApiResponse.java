package com.louter.uhd.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class ApiResponse<T> {
    private T data;

    @NotNull(message = "성공여부는 필수 입력갑입니다.")
    private Boolean success;

    private String message;

    private ErrorResponse error;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(true)
                .data(null)
                .message(null)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<Map<String, Object>> successAsMap(String key, T value) {
        Map<String, Object> obj = new HashMap<>();
        obj.put(key, value);

        return ApiResponse.<Map<String, Object>>builder()
                .success(true)
                .data(obj)
                .message(null)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<Map<String, Object>> successAsMap(String key, T value, String message) {
        Map<String, Object> obj = new HashMap<>();
        obj.put(key, value);

        return ApiResponse.<Map<String, Object>>builder()
                .success(true)
                .data(obj)
                .message(message)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorResponse error) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(null)
                .error(error)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorResponse error, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .error(error)
                .build();
    }
}
