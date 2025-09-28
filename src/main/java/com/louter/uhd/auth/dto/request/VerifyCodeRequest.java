package com.louter.uhd.auth.dto.request;

import com.louter.uhd.auth.domain.VerificationPurpose;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequest {
    @NotNull
    private String userEmail;
    @NotNull
    private String code;
    @NotNull
    private VerificationPurpose purpose;
}
