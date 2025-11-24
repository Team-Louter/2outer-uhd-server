package com.louter.uhd.post.dto.response;

import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.domain.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatePostResponse {
    @NotNull
    private Long postId;
    @NotNull
    private String userId;
    @NotNull
    private String postTitle;
    @NotNull
    private String postContent;
    @NotNull
    private String postImage;
    @NotNull
    private Status postStatus;
    @NotNull
    private LocalDateTime postCreatedAt;
    @NotNull
    private int viewers;

    public static CreatePostResponse from(Post post) {
        return CreatePostResponse.builder()
                .postId(post.getPostId())
                .userId(post.getUser().getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postImage(post.getPostImage())
                .postStatus(post.getPostStatus())
                .postCreatedAt(post.getPostCreatedAt())
                .viewers(post.getPostViewers())
                .build();
    }
}
