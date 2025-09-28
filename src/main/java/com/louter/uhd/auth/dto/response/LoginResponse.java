package com.louter.uhd.auth.dto.response;

import com.louter.uhd.auth.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class LoginResponse {
    @NotNull
    private Boolean success;
    @NotNull
    private String userId;
    @NotNull
    private String token;

    public static LoginResponse from(Map<User, Object> response) {
        Map.Entry<User, Object> entry = response.entrySet().iterator().next();
        User user = entry.getKey();
        String token = String.valueOf(entry.getValue());

        return LoginResponse.builder()
                .success(true)
                .userId(user.getUserId())
                .token(token)
                .build();
    }
}