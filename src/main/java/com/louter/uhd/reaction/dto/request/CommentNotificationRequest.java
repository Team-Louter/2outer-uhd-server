package com.louter.uhd.reaction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentNotificationRequest {
    @NotNull
    private String ownerUserId;

    @NotNull
    private String ownerEmail;

    @NotNull
    private String commenterUserId;

    @NotNull
    private String commentContent;

    @NotNull
    private Long commentId;

    @NotNull
    private Long postId;
}
