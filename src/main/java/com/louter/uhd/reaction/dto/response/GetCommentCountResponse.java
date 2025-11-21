package com.louter.uhd.reaction.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class GetCommentCountResponse {
    @NotBlank
    private String postTitle;
    @NotNull
    private Long postCount;

    public static GetCommentCountResponse from(Map<String, Long> response) {
        Map.Entry<String, Long> entry = response.entrySet().iterator().next();
        String postTitle = entry.getKey();
        Long postCount = entry.getValue();

        return GetCommentCountResponse.builder()
                .postTitle(postTitle)
                .postCount(postCount)
                .build();
    }
}
