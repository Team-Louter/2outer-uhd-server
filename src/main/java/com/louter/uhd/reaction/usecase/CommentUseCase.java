package com.louter.uhd.reaction.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.repository.UserRepository;
import com.louter.uhd.auth.usecase.FindCurrentUserUseCase;
import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.exception.ForbiddenAccessException;
import com.louter.uhd.post.exception.PostNotFoundException;
import com.louter.uhd.post.repository.PostRepository;
import com.louter.uhd.reaction.domain.Comment;
import com.louter.uhd.reaction.dto.request.CommentNotificationRequest;
import com.louter.uhd.reaction.dto.request.CreateCommentRequest;
import com.louter.uhd.reaction.dto.request.UpdateCommentRequest;
import com.louter.uhd.reaction.exception.CommentNotFoundException;
import com.louter.uhd.reaction.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentUseCase {
    // 디비 이용
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    // 함수 호출
    private final NotificationUseCase notificationUseCase;
    private final FindCurrentUserUseCase findCurrentUserUseCase;

    // 댓글 생성
    @Transactional
    public Comment createComment(CreateCommentRequest request) {
        // 유저 조회
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 게시글 조회
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        // 댓글 생성
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .commentContent(request.getCommentContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        // 본인이 작성한 글에 남긴 댓글이 아닐 때만 알림 전송
        if(!post.getUser().getUserId().equals(user.getUserId())) {
            CommentNotificationRequest commentNotificationRequest = CommentNotificationRequest.builder()
                    .ownerUserId(post.getUser().getUserId())
                    .ownerEmail(post.getUser().getUserEmail())
                    .commenterUserId(user.getUserId())
                    .commentContent(comment.getCommentContent())
                    .commentId(savedComment.getCommentId())
                    .postId(post.getPostId())
                    .build();

            notificationUseCase.sendCommentNotification(commentNotificationRequest);
        }

        return savedComment;
    }

    // 특정 댓글 조회
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    // 특정 게시글의 댓글 목록 조회 (최신순)
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPost_PostIdOrderByCommentCreatedAtDesc(postId);
    }

    // 특정 유저가 작성한 댓글 목록 조회 (최신순)
    public List<Comment> getCommentsByUser(String userId) {
        return commentRepository.findByUser_UserIdOrderByCommentCreatedAtDesc(userId);
    }

    // 특정 게시글의 댓글 수 조회
    public Map<String, Long> getCommentCount(Long postId) {
        Long commentCount = commentRepository.countByPost_PostId(postId);

        return Map.of(postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글이 조회되지 않음"))
                .getPostTitle(), commentCount);
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        String userId = findCurrentUserUseCase.getCurrentUser().getUserId();

        // 작성자 확인
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ForbiddenAccessException("본인이 작성한 댓글만 수정 가능");
        }

        // 댓글 수정
        comment.setCommentContent(request.getCommentContent());

        return commentRepository.save(comment);
    }

    // 댓글 삭제 (일반 유저)
    @Transactional
    public void deleteComment(Long commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        // 작성자 확인
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ForbiddenAccessException("본인이 작성한 댓글만 삭제가능");
        }

        commentRepository.delete(comment);
    }

    // 댓글 삭제 (관리자)
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 조회되지 않음"));
        commentRepository.delete(comment);
    }
}
