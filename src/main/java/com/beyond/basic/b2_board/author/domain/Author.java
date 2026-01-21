package com.beyond.basic.b2_board.author.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

//저자(Author)엔티티 도메인 -name, email, password
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
//빌더패턴은 AllArgs생성자 기반으로 동작한다.
@Builder
//JPA에게 Entity관리를 위임하기 위한 어노테이션
@Entity
public class Author {
    @Id //pk설정
    private Long id;
    private String name;
    private String email;
    private String password;
}
