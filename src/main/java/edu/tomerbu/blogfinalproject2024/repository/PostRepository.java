package edu.tomerbu.blogfinalproject2024.repository;

import edu.tomerbu.blogfinalproject2024.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
