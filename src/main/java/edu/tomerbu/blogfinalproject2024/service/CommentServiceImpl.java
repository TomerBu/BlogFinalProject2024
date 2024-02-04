package edu.tomerbu.blogfinalproject2024.service;


import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Comment;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import edu.tomerbu.blogfinalproject2024.repository.CommentRepository;
import edu.tomerbu.blogfinalproject2024.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    //props:
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentResponseDTO createComment(long postId, CommentRequestDTO dto){
        Post post = postService.getPostEntityOrThrow(postId);

        //map the dto to a Comment entity
        var comment = modelMapper.map(dto, Comment.class);

        //comment belongs to the Post
        comment.setPost(post);

        //save the comment
        var saved = commentRepository.save(comment);

        //return the response dto:
        return modelMapper.map(saved, CommentResponseDTO.class);
    }
}
