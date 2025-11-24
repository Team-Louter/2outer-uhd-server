package com.louter.uhd.post.dto.response;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FindDetailedPostInfoResponse {
    @NotNull
    private Long postId;

    @NotBlank
    private String userId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

    private String postImage;

    @NotNull
    private Status postStatus;

    @NotNull
    private LocalDateTime postCreatedAt;

    @NotNull
    private Integer viewers;

    public static FindDetailedPostInfoResponse from(Post post) {
        User user = post.getUser();
        return FindDetailedPostInfoResponse.builder()
                .postId(post.getPostId())
                .userId(user.getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postImage(post.getPostImage())
                .postStatus(post.getPostStatus())
                .postCreatedAt(post.getPostCreatedAt())
                .viewers(post.getPostViewers())
                .build();
    }
}
