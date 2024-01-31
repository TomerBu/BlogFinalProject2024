package edu.tomerbu.blogfinalproject2024.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDTO {
    //title, description, content
    @NotNull
    @Size(min = 2, max = 128)
    private String title;

    @NotNull
    @Size(min = 2, max = 512)
    private String description;

    @NotNull
    @Size(min = 2)
    private String content;
}
