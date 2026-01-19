package com.beyond.basic.b2_board.author.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
//dto계층은 엔티티만큼의 안정성을 우선하기보다는, 편의를 위해 setter도 일반적으로 추가.
@Data
public class AuthorCreateDto {
    private String name;
    private String email;
    private String password;
}
