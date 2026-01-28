package com.beyond.basic.b2_board.author.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Entity
public class Address {
    @Id //pk설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String zipCode;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id", unique = true,
//            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)//fk에대한 옵션
//    private Author author;
}
