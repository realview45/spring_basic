package com.beyond.basic.b2_board.author.domain;

import lombok.*;

//저자(Author)엔티티 도메인 -name, email, password
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
//빌더패턴은 AllArgs생성자 기반으로 동작한다.
@Builder
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
