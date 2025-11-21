package com.louter.uhd.reaction.domain;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.post.domain.Post;
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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_id", nullable = false)
    private Long commentId;

    @Column(name = "co_content", nullable = false, length = 500)
    private String commentContent;

    @CreationTimestamp
    @Column(name = "co_created_at", nullable = false, updatable = false)
    private LocalDateTime commentCreatedAt;

    @ManyToOne
    @JoinColumn(name = "fk_u_email", nullable = false, updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_p_id", nullable = false, insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
