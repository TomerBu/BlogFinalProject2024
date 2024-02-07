package edu.tomerbu.blogfinalproject2024.controller;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.repository.CommentRepository;
import edu.tomerbu.blogfinalproject2024.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentRepository commentRepository;

    @GetMapping
    //GET /api/v1/posts?pageNo=3&pageSize=25&sortDir=asc&sortBy=title
    //GET /api/v1/posts?pageNo=3&pageSize=25&sortDir=asc
    //GET /api/v1/posts?pageNo=3&pageSize=25
    //GET /api/v1/posts?pageNo=3
    //GET /api/v1/posts
    public ResponseEntity<PostListDTO> getAllPosts(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String... sortBy
    ) {


        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortDir, sortBy));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
