package edu.tomerbu.blogfinalproject2024.repository;

import edu.tomerbu.blogfinalproject2024.entity.Comment;
import edu.tomerbu.blogfinalproject2024.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    //JPA Derived Query methods:
    List<Comment> findCommentsByPostId(long postId);
    List<Comment> findCommentsByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
    List<Comment> findCommentsByEmailIgnoreCase(String email);
    List<Comment> findCommentsByUsernameIgnoreCase(String username);
}
