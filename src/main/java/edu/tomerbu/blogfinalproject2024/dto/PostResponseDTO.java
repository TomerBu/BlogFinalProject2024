package edu.tomerbu.blogfinalproject2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    private Long id;
    private String title;

    private String description;

    private String content;

    private LocalDateTime createdAt;

    private List<CommentResponseDTO> comments;
}
