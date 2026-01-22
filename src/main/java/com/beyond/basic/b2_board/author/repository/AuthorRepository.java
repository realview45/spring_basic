package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//가장중요구리
//SpringDataJpa를 사용하기 위해서는 JpaRepository인터페이스를 상속해야하고, 상속시에 Entity명과 pk타입을 제네릭에 설정
//JpaRepository를 상속함으로서 JpaRepository의 주요기능(각종CRUD)상속
@Repository                                 //인터페이스구리 hibernate에 구현구리
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    save, findById, findAll, delete등은 사전에 구현

//    그외에 다른컬럼으로 조회할 때에는 findBy+컬럼명 형식으로 선언하면 실행시점 자동구현.
    Optional<Author> findAllByEmail(String email);
}
