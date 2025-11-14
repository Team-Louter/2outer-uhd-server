package com.example.clubfind.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "find")
public class Find {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="f_id")
    private Long id;

    @Column(length = 20, name = "f_name")
    private String name;

    @Column(length = 40, name = "f_loca")
    private String location;

    @Column(length = 100, name = "f_expla")
    private String explain;

    @Column(name = "f_cate")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "f_image")
    private String imagePath;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


}
