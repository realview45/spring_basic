package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.common.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//저자(Author)엔티티 도메인 -name, email, password
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
//빌더패턴은 AllArgs생성자 기반으로 동작한다.
@Builder
//JPA에게 Entity관리를 위임하기 위한 어노테이션
@Entity
public class Author extends BaseTimeEntity {
    @Id //pk설정
//    identity : auto_increment설정. auto:id생섯전략을 jpa에게 자동설정하도록 위임.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long -> bigint, String -> varchar, LocalDateTime -> datetime, enum, boolean
    private Long id;
//    변수명이 컬럼명으로 그대로 생성. camel case는 언더스코어로 변경. 예)nickName -> nick_name
    private String name;
//    길이(varchar50, 디폴트-varchar255), 제약조건(unique, not null)설정
    @Column(length=50,unique=true,nullable=false)
    private String email;
//    @Column(name = "pw") : 컬럼명의 변경이 가능하나, 일반적으로 일치시킴.
//    @Setter
    private String password;
//    enum타입은 내부적으로 숫자값을 가지고 있으나, 문자형태로 저장하겠다는 어노테이션
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

//    일반적으로 OneToMany는 선택사항. ManyToOne은 필수사항.
//    mappedBy : ManyToOne쪽의 변수명을 문자열로 지정. -> 조회해야할 컬럼을 명시
//    연관관계(fk)의 주인설정 -> 연관관계의 주인은 author변수를 가지고 있는 Post에 있음을 명시
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    List<Post> postList = new ArrayList<>();


    public void updatePassword(String password){
        this.password = password;
    }
}
