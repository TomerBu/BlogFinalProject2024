package edu.tomerbu.blogfinalproject2024.service;

import edu.tomerbu.blogfinalproject2024.dto.PostCreateDTO;
import edu.tomerbu.blogfinalproject2024.dto.PostListDto;
import edu.tomerbu.blogfinalproject2024.dto.PostResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import edu.tomerbu.blogfinalproject2024.error.PaginationException;
import edu.tomerbu.blogfinalproject2024.error.ResourceNotFoundException;
import edu.tomerbu.blogfinalproject2024.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @Override  //getAllPosts(1, 10, "asc", "title", "author", "releaseDate")
    public PostListDto getAllPosts(int pageNo, int pageSize, String sortDir, String... sortBy) {

        try {
            //Direction from string ('asc')
            Sort.Direction sort = Sort.Direction.fromString(sortDir);
            //build the page request
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort, sortBy);

            //get the page result from the repository:
            Page<Post> pr = postRepository.findAll(pageable);

            if (pageNo > pr.getTotalPages()) {
                throw new PaginationException("Page Number " + pageNo + " Exceeds totalPages " + pr.getTotalPages());
            }

            List<PostResponseDTO> postListDto =
                    pr.getContent().stream()
                            .map(p -> modelMapper.map(p, PostResponseDTO.class))
                            .toList();

            return new PostListDto(
                    pr.getTotalElements(),
                    pr.getNumber(),
                    pr.getSize(),
                    pr.getTotalPages(),
                    pr.isFirst(),
                    pr.isLast(),
                    postListDto
            );
        } catch (IllegalArgumentException e) {
            throw new PaginationException(e.getMessage());
        }
    }

    @Override
    public PostResponseDTO getPostById(long id) {
        Post post = getPostEntityOrThrow(id);
        return modelMapper.map(post, PostResponseDTO.class);
    }


    @Override
    public Post getPostEntityOrThrow(long id) {
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

    @Override
    public PostResponseDTO deletePost(long id) {

        Post post = getPostEntityOrThrow(id);

        postRepository.deleteById(id);
        return modelMapper.map(post, PostResponseDTO.class);
    }
}
















