package com.louter.uhd.post.dto.response;

import com.louter.uhd.post.domain.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPostResponse {
    @NotNull
    private String postTitle;

    public static FindPostResponse from(Post post) {
        return FindPostResponse.builder()
                .postTitle(post.getPostTitle())
                .build();
    }
}
