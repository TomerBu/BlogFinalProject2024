package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;

public interface CommentService {
    CommentResponseDTO createComment(long postId, CommentRequestDTO dto);
}
