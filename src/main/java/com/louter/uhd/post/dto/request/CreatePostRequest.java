package com.louter.uhd.post.dto.request;

import com.louter.uhd.post.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String postTitle;

    @NotNull
    private String postContent;

    private String postImage;

    @NotNull
    private Status postStatus;
}
