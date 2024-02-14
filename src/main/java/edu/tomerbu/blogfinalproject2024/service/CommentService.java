package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.CommentListDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import org.springframework.security.core.Authentication;

public interface CommentService {
    CommentResponseDTO createComment(long postId, CommentRequestDTO dto, Authentication authentication);

    CommentListDTO findCommentsByPostId(long id);

    CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto, Authentication authentication);

    CommentResponseDTO deleteCommentById(long id, Authentication authentication);
}
