package edu.tomerbu.blogfinalproject2024.controller;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDto;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostListDto> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePostById(
            @PathVariable long id,
            @RequestBody @Valid PostCreateDTO dto
    ) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestBody @Valid PostCreateDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        var res = postService.createPost(dto);

        var uri = uriBuilder.path("/api/v1/posts/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(uri).body(res);
    }
}
