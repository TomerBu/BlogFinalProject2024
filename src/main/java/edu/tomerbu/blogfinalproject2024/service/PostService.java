package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDto;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;

public interface PostService {
    PostResponseDTO createPost(PostCreateDTO dto);

    PostListDto getAllPosts();

    PostResponseDTO getPostById(long id);

    PostResponseDTO updatePost(long id, PostCreateDTO dto);
}
