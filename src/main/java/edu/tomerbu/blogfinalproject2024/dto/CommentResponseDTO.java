package edu.tomerbu.blogfinalproject2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
