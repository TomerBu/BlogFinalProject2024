package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.CommentListDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO createComment(long postId, CommentRequestDTO dto);

    CommentListDTO findCommentsByPostId(long id);

    CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto);

    CommentResponseDTO deleteCommentById(long id);
}
