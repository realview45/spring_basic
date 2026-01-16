package com.beyond.basic.b2_board.author.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//저자(Author)엔티티 도메인 -name, email, password
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
