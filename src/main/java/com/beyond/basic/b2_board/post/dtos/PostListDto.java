package com.beyond.basic.b2_board.post.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostListDto {
    private Long id;
    private String title;
    private String category;
    private String authorEmail;
    private String delYn;

    public static PostListDto fromEntity(Post post){
        return PostListDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .authorEmail(post.getAuthor().getEmail())
                .delYn(post.getDelYn())
                .build();
    }
}
