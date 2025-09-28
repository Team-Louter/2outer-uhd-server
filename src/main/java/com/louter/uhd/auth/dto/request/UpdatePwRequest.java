package com.louter.uhd.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePwRequest {
    @NotNull
    private String userId;
    @NotNull
    private String userPassword;
}
