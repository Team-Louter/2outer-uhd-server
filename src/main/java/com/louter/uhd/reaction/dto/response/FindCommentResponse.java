package com.louter.uhd.reaction.dto.response;

import com.louter.uhd.reaction.domain.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class FindCommentResponse {
    @NotNull
    private List<CreateCommentResponse> comments;
    @NotNull
    private Integer totalCount;

    public static FindCommentResponse from(List<Comment> comments) {
        List<CreateCommentResponse> commentResponses = comments.stream()
                .map(CreateCommentResponse::from)
                .collect(Collectors.toList());

        return FindCommentResponse.builder()
                .comments(commentResponses)
                .totalCount(commentResponses.size())
                .build();
    }
}
