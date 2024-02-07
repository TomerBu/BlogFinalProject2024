package edu.tomerbu.blogfinalproject2024.service;


import edu.tomerbu.blogfinalproject2024.dto.CommentListDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Comment;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import edu.tomerbu.blogfinalproject2024.error.ResourceNotFoundException;
import edu.tomerbu.blogfinalproject2024.repository.CommentRepository;
import edu.tomerbu.blogfinalproject2024.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    //props:
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentResponseDTO createComment(long postId, CommentRequestDTO dto) {
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

    @Override
    public CommentListDTO findCommentsByPostId(long id) {
        postService.getPostEntityOrThrow(id);

        return new CommentListDTO(
                commentRepository.findCommentsByPostId(id).stream().map(
                        c -> modelMapper.map(c, CommentResponseDTO.class)
                ).toList()
        );
    }

    @Override
    public CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto) {
        //check it exists
        Comment comment = getCommentEntityOrThrow(id);


        var commentBeforeSave = modelMapper.map(dto, Comment.class);
        commentBeforeSave.setId(id);
        commentBeforeSave.setPost(comment.getPost());
        commentBeforeSave.setCreatedAt(comment.getCreatedAt());

        var saved = commentRepository.save(commentBeforeSave);
        return modelMapper.map(saved, CommentResponseDTO.class);
    }

    private Comment getCommentEntityOrThrow(long id) {
        return commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException.newInstance("Comment", "id", id)
        );
    }

    @Override
    public CommentResponseDTO deleteCommentById(long id) {
        var comment = getCommentEntityOrThrow(id);
        commentRepository.delete(comment);
        return modelMapper.map(comment, CommentResponseDTO.class);
    }

}


















