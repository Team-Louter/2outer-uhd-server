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
public class SendVerificationEmailResponse {
    @NotNull
    private Boolean success;
    @NotNull
    private String userEmail;
    @NotNull
    private String code;

    public static SendVerificationEmailResponse from(Map<User, String> response) {
        Map.Entry<User, String> entry = response.entrySet().iterator().next();
        User user = entry.getKey();
        String code = entry.getValue();

        return SendVerificationEmailResponse.builder()
                .success(true)
                .userEmail(user.getUserEmail())
                .code(code)
                .build();
    }
}
