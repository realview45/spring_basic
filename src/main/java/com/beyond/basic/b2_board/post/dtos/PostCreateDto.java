package com.beyond.basic.b2_board.post.dtos;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostCreateDto {
    @NotBlank
    private String title;
    private String contents;
    private String category;
    @NotBlank
    private String authorEmail;
    public Post toEntity(){
        return Post.builder()
                .title(title)
                .contents(contents)
                .category(category)
                .authorEmail(authorEmail)
                .delYn("N")
                .build();
    }
}