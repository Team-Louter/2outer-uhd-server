package com.louter.uhd.post.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.repository.UserRepository;
import com.louter.uhd.auth.usecase.FindCurrentUserUseCase;
import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.domain.Status;
import com.louter.uhd.post.dto.request.CreatePostRequest;
import com.louter.uhd.post.dto.request.SearchPostsRequest;
import com.louter.uhd.post.dto.request.UpdatePostRequest;
import com.louter.uhd.post.exception.ForbiddenAccessException;
import com.louter.uhd.post.exception.PostNotFoundException;
import com.louter.uhd.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostUseCase {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FindCurrentUserUseCase findCurrentUserUseCase;

    // 게시글 생성
    @Transactional
    public Post createPost(CreatePostRequest request) {
        // 유저 조회
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        // 게시글 생성
        Post post = Post.builder()
                .user(user)
                .postTitle(request.getPostTitle())
                .postContent(request.getPostContent())
                .postImage(request.getPostImage())
                .postStatus(request.getPostStatus())
                .build();

        return postRepository.save(post);
    }

    // 특정 게시글 조회
    @Transactional
    public Post getPost(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        // 조회수 증가
        post.setPostViewers(post.getPostViewers() + 1);
        postRepository.save(post);

        return post;
    }

    // 특정 채널의 게시물 목록 조회(최신순)
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByPostCreatedAtDesc();
    }

    // 특정 유저의 게시물 목록 조회(최신순)
    public List<Post> getPostsByUser(String userId) {
        return postRepository.findByUser_UserIdOrderByPostCreatedAtDesc(userId);
    }

    // 게시글 검색
    public List<Post> searchPosts(SearchPostsRequest request) {
        return postRepository.searchByKeyword("%" + request.getKeyword() + "%");
    }

    // 인증 게시글 상태별 조회
    public List<Post> getPostsByStatus(String status) {
        Status postStatus;
        if (status.equals("FIND"))
            postStatus = Status.FIND;
        else
            postStatus = Status.LOST;
        return postRepository.findByPostStatusOrderByPostCreatedAtDesc(postStatus);
    }

    // 게시글 수정
    @Transactional
    public Post updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        // 작성자 확인
        if (!post.getUser().getUserId().equals(request.getUserId())) {
            throw new ForbiddenAccessException("본인이 작성한 게시글만 수정 가능");
        }

        // 게시글 수정
        post.setPostTitle(request.getPostTitle());
        post.setPostContent(request.getPostContent());
        post.setPostImage(request.getPostImage());
        return postRepository.save(post);
    }

    // 게시글 삭제(유저 본인)
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        String userId = findCurrentUserUseCase.getCurrentUser().getUserId();

        // 작성자 확인
        if (!post.getUser().getUserId().equals(userId)) {
            throw new ForbiddenAccessException("본인이 작성한 게시글만 삭제 가능");
        }

        postRepository.delete(post);
    }

    // 게시글 삭제 (관리자)
    @Transactional
    public void deletePostByAdmin(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        postRepository.delete(post);
    }

    @Transactional
    public Post findDetailedPost(String postTitle) {
        Post post = postRepository.findByPostTitle(postTitle)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        post.setPostViewers(post.getPostViewers() + 1);

        return postRepository.save(post);
    }
}