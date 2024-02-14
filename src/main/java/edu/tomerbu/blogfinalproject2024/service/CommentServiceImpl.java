package edu.tomerbu.blogfinalproject2024.service;


import edu.tomerbu.blogfinalproject2024.dto.CommentListDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentRequestDTO;
import edu.tomerbu.blogfinalproject2024.dto.CommentResponseDTO;
import edu.tomerbu.blogfinalproject2024.entity.Comment;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import edu.tomerbu.blogfinalproject2024.entity.User;
import edu.tomerbu.blogfinalproject2024.error.AuthenticationException;
import edu.tomerbu.blogfinalproject2024.error.ResourceNotFoundException;
import edu.tomerbu.blogfinalproject2024.repository.CommentRepository;
import edu.tomerbu.blogfinalproject2024.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    //props:
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDTO createComment(long postId, CommentRequestDTO dto, Authentication authentication) {
        Post post = postService.getPostEntityOrThrow(postId);

        var user = getUserEntityOrThrow(authentication);

        //map the dto to a Comment entity
        var comment = modelMapper.map(dto, Comment.class);

        //comment belongs to the Post
        comment.setPost(post);
        comment.setUser(user);
        //save the comment
        var saved = commentRepository.save(comment);
        //return the response dto:
        return  modelMapper.map(saved, CommentResponseDTO.class);
    }

    private User getUserEntityOrThrow(Authentication authentication) {
        return userRepository.findUserByUsernameIgnoreCase(authentication.getName()).orElseThrow(
                () -> new AuthenticationException("User not found " + authentication.getName())
        );
    }

    private User checkPermissions(Authentication authentication, Comment comment) {
        var userFromDb = getUserEntityOrThrow(authentication);

        boolean isAdmin = userFromDb
                .getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ROLE_ADMIN"));

        if (!isAdmin && !Objects.equals(comment.getUser().getId(), userFromDb.getId())) {
            throw new AuthenticationException("User not found " + authentication.getName());
        }

        return userFromDb;
    }

    @Override
    public CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto, Authentication authentication) {
        //check it exists
        Comment comment = getCommentEntityOrThrow(id);

        var user = checkPermissions(authentication, comment);

        var commentBeforeSave = modelMapper.map(dto, Comment.class);
        commentBeforeSave.setId(id);
        commentBeforeSave.setPost(comment.getPost());
        commentBeforeSave.setCreatedAt(comment.getCreatedAt());
        commentBeforeSave.setUser(user);

        var saved = commentRepository.save(commentBeforeSave);
        return modelMapper.map(saved, CommentResponseDTO.class);
    }

    @Override
    public CommentResponseDTO deleteCommentById(long id, Authentication authentication) {
        var comment = getCommentEntityOrThrow(id);

        checkPermissions(authentication, comment);
        commentRepository.delete(comment);
        return modelMapper.map(comment, CommentResponseDTO.class);
    }


    private Comment getCommentEntityOrThrow(long id) {
        return commentRepository.findById(id).orElseThrow(
                ResourceNotFoundException.newInstance("Comment", "id", id)
        );
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
}


















