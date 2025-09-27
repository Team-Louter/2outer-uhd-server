package com.louter.uhd.auth.dto.response;

import com.louter.uhd.auth.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdatePwResponse {
    @NotNull
    private Boolean success;
    @NotNull
    private String userId;

    public static UpdatePwResponse from(User user) {
        return UpdatePwResponse.builder()
                .success(true)
                .userId(user.getUserId())
                .build();
    }
}
