package edu.tomerbu.blogfinalproject2024.controller;


import edu.tomerbu.blogfinalproject2024.dto.CommentListDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    //props:
    private final CommentService commentService;

    //POST /api/v1/posts/{id}/comments
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable(name = "id") long postId,
            @Valid @RequestBody CommentRequestDTO dto,
            UriComponentsBuilder uriBuilder) {
        var saved = commentService.createComment(postId, dto);

        var uri = uriBuilder
                .path("/posts/{id}/comments")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }


    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<CommentListDTO> getCommentsByPostId(
            @PathVariable(name = "id") long postId
    ) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable long id, @RequestBody @Valid CommentRequestDTO dto
    ) {
        return ResponseEntity.ok(commentService.updateCommentById(id, dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long id) {
        return ResponseEntity.ok(commentService.deleteCommentById(id));
    }
}
