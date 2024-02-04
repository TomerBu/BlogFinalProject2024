package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDto;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Post;

public interface PostService {
    PostResponseDTO createPost(PostCreateDTO dto);

    PostListDto getAllPosts(int pageNo, int pageSize, String sortDir, String... sortBy);

    PostResponseDTO getPostById(long id);

    PostResponseDTO updatePost(long id, PostCreateDTO dto);

    PostResponseDTO deletePost(long id);

    Post getPostEntityOrThrow(long id);
}
