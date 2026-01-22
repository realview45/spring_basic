package com.beyond.basic.b2_board.post.domain;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
@Builder
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length=3000)
    private String contents;
    private String category;
//    @Column(nullable = false)
//    private Long authorId;

    @ManyToOne//fk설정
//    @JoinColumn()//fk에대한 옵션
    private Author author;
    @Builder.Default
    private String delYn="N";

    public void deleteDelYn(){
        delYn = "Y";
    }
}
