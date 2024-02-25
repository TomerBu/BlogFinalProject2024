package edu.tomerbu.blogfinalproject2024.controller;

import edu.tomerbu.blogfinalproject2024.dto.ErrorDto;
import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.repository.CommentRepository;
import edu.tomerbu.blogfinalproject2024.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(
        name = "Post Controller",
        description = "Blog Posts"
)
public class PostController {

    private final PostService postService;
    private final CommentRepository commentRepository;

    @GetMapping
    @Operation(summary = "Get All Blog Posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Posts"),
            @ApiResponse(responseCode = "400",
                    description = "Negative Page Size / PageNo",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class))
                    }
            ),
            @ApiResponse(responseCode = "500", description = "Something went wrong")
    }
    )
    public ResponseEntity<PostListDTO> getAllPosts(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String... sortBy
    ) {


        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortDir, sortBy));
    }

    @Operation(summary = "Get a post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDTO> updatePostById(
            @PathVariable long id,
            @RequestBody @Valid PostCreateDTO dto
    ) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestBody @Valid PostCreateDTO dto,
            UriComponentsBuilder uriBuilder
    ) {
        var res = postService.createPost(dto);

        var uri = uriBuilder.path("/api/v1/posts/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(uri).body(res);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
