package edu.tomerbu.blogfinalproject2024.controller;


import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
            UriComponentsBuilder uriBuilder){
        var saved = commentService.createComment(postId, dto);

        var uri = uriBuilder
                .path("/posts/{id}/comments")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }
}
