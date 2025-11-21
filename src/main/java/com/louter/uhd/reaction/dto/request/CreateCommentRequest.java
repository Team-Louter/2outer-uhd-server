package com.louter.uhd.reaction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotNull
    private String userId;

    @NotNull
    private Long postId;

    @NotNull
    private String commentContent;
}
