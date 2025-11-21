package com.louter.uhd.post.dto.response;

import com.louter.uhd.post.domain.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class FindPostResponse {
    @NotNull
    private List<CreatePostResponse> posts;
    @NotNull
    private Integer totalCount;

    public static FindPostResponse from(List<Post> posts) {
        List<CreatePostResponse> postResponses = posts.stream()
                .map(CreatePostResponse::from)
                .collect(Collectors.toList());

        return FindPostResponse.builder()
                .posts(postResponses)
                .totalCount(postResponses.size())
                .build();
    }
}
