package com.louter.uhd.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FindDetailedPostInfoRequest {
    @NotBlank
    private String postTitle;
}
