package com.louter.uhd.post.controller;

import com.louter.uhd.common.dto.ApiResponse;
import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.dto.request.CreatePostRequest;
import com.louter.uhd.post.dto.request.SearchPostsRequest;
import com.louter.uhd.post.dto.request.UpdatePostRequest;
import com.louter.uhd.post.dto.response.CreatePostResponse;
import com.louter.uhd.post.dto.response.FindDetailedPostInfoResponse;
import com.louter.uhd.post.dto.response.FindPostResponse;
import com.louter.uhd.post.usecase.PostUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostUseCase postUseCase;

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post post = postUseCase.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(CreatePostResponse.from(post)));
    }

    // 특정 게시글 조회
    @GetMapping("/find/{postId}")
    public ResponseEntity<ApiResponse<CreatePostResponse>> findPost(@PathVariable Long postId) {
        Post post = postUseCase.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success(CreatePostResponse.from(post)));
    }

    // 게시글 목록 조회
    @GetMapping("/find/all")
    public ResponseEntity<ApiResponse<List<FindPostResponse>>> getPostsByChannel() {
        List<Post> posts = postUseCase.getPosts();
        return ResponseEntity.ok(ApiResponse.success(
                posts.stream()
                    .map(FindPostResponse::from)
                    .toList()
        ));
    }

    // 특정 유저의 게시글 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FindPostResponse>>> getPostsByUser(@PathVariable String userId) {
        List<Post> posts = postUseCase.getPostsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(
                posts.stream()
                        .map(FindPostResponse::from)
                        .toList()
        ));
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FindPostResponse>>> searchPosts(@ModelAttribute SearchPostsRequest request) {
        List<Post> posts = postUseCase.searchPosts(request);
        return ResponseEntity.ok(ApiResponse.success(
                posts.stream()
                        .map(FindPostResponse::from)
                        .toList()
        ));
    }

    // 게시글 세부 정보 검색
    @GetMapping("/find/{postTitle}")
    public ResponseEntity<ApiResponse<FindDetailedPostInfoResponse>> findDetailedPostInfo(@PathVariable String postTitle) {
        Post post = postUseCase.findDetailedPost(postTitle);
        return ResponseEntity.ok(ApiResponse.success(FindDetailedPostInfoResponse.from(post)));
    }


    // 게시글 수정
    @PutMapping("/update/{postId}")
    public ResponseEntity<ApiResponse<CreatePostResponse>> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        Post post = postUseCase.updatePost(postId, request);
        return ResponseEntity.ok(ApiResponse.success(CreatePostResponse.from(post)));
    }

    // 게시글 삭제(유저 본인)
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postUseCase.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // FIND 게시물 조회
    @GetMapping("/find/{status}")
    public ResponseEntity<ApiResponse<List<FindPostResponse>>> findPostsByPostStatus(@PathVariable String status) {
        List<Post> posts = postUseCase.getPostsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(
                posts.stream()
                .map(FindPostResponse::from)
                .toList()
        ));
    }
}
