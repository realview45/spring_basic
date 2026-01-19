package com.beyond.basic.b2_board.author.domain;

import lombok.*;

//저자(Author)엔티티 도메인 -name, email, password
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
@Builder
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
