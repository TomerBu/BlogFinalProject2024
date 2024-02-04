package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDto;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import edu.tomerbu.blogfinalproject2024.error.ResourceNotFoundException;
import edu.tomerbu.blogfinalproject2024.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    //props:
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @Override
    public PostResponseDTO createPost(PostCreateDTO dto) {
        //1) convert the dto to entity
        Post post = modelMapper.map(dto, Post.class);

        //2) saved = save
        var saved = postRepository.save(post);

        System.out.println();

        //3) return response dto
        return modelMapper.map(saved, PostResponseDTO.class);
    }

    @Override
    public PostListDto getAllPosts() {
        List<PostResponseDTO> posts =
                postRepository
                        .findAll()
                        .stream()
                        .map(p -> modelMapper.map(p, PostResponseDTO.class))
                        .toList();

        return new PostListDto(posts);
    }

    @Override
    public PostResponseDTO getPostById(long id) {
        Post post = getPostEntityOrThrow(id);
        return modelMapper.map(post, PostResponseDTO.class);
    }

    private Post getPostEntityOrThrow(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostResponseDTO updatePost(long id, PostCreateDTO dto) {
        //check that it exists:
        Post post = getPostEntityOrThrow(id);

        //update all the entity props:
        post.setContent(dto.getContent());
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());

        //save to update.
        var saved = postRepository.save(post);

        //return the response.
        return modelMapper.map(saved, PostResponseDTO.class);
    }
}
















