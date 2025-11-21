package com.louter.uhd.post.repository;

import com.louter.uhd.post.domain.Post;
import com.louter.uhd.post.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 특정 채널의 모든 게시글 조회(최신순)
    List<Post> findAllByOrderByPostCreatedAtDesc();

    // 특정 유저의 모든 게시글 조회(최신순)
    List<Post> findByUser_UserIdOrderByPostCreatedAtDesc(String userId);

    // 게시글 조회 - postId
    Optional<Post> findByPostId(Long postId);

    // 특정 키워드를 제목이나 설명에 포함한 게시글 조회(최신순)
    @Query("""
        select p from Post p
        where p.postTitle like concat('%', :keyword, '%')
            or p.postContent like concat('%', :keyword, '%')
        order by p.postTitle asc
    """)
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    // 상태 별 게시글 조회
    List<Post> findByPostStatus(Status postStatus);
}
