package com.louter.uhd.post.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    @NotNull
    private String userId;

    @NotNull
    private String postTitle;

    @NotNull
    private String postContent;

    private String postImage;
}
