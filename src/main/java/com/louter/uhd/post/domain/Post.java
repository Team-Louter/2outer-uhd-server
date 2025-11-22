package com.louter.uhd.post.domain;

import com.louter.uhd.auth.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id", nullable = false)
    private Long postId;

    @Column(name = "p_title", nullable = false)
    private String postTitle;

    @Column(name = "p_content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String postContent;

    @Column(name = "p_image")
    private String postImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status")
    private Status postStatus;

    @CreationTimestamp
    @Column(name = "p_created_at", nullable = false, updatable = false)
    private LocalDateTime postCreatedAt;

    @Column(name = "p_viewers", nullable = false)
    @Builder.Default
    private Integer postViewers = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_u_id", referencedColumnName = "u_id", nullable = false, updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
