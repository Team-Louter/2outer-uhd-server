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
public class VerifyCodeResponse {
    @NotNull
    private Boolean success;
    @NotNull
    private String userEmail;

    public static VerifyCodeResponse from(User user) {
        return VerifyCodeResponse.builder()
                .success(true)
                .userEmail(user.getUserEmail())
                .build();
    }
}
