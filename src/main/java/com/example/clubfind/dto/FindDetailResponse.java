package com.example.clubfind.dto;

import com.example.clubfind.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindDetailResponse {
    private Long id;

    private String name;

    private String location;

    private String explain;

    private Category category;

    private String imagePath;

    private LocalDateTime createdAt;
}
