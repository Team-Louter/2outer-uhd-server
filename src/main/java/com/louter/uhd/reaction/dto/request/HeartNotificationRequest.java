package com.louter.uhd.reaction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeartNotificationRequest {
    @NotNull
    private String ownerUserId;

    @NotNull
    private String ownerEmail;

    @NotNull
    private String likerUserId;

    @NotNull
    private Long heartId;

    @NotNull
    private Long postId;
}
