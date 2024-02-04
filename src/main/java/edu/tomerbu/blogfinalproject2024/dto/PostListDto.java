package edu.tomerbu.blogfinalproject2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListDto {

    //pageSize... etc
    private Collection<PostResponseDTO> posts;
}
