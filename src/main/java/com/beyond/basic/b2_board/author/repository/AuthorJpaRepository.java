package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJpaRepository {
    void save(Author author){

    }
    Optional<Author> findById(Long id){
        return null;
    }
    Optional<Author> findByEmail(String email){
        return null;
    }
    List<Author> findAll(){
        return null;
    }
    void delete(Long id){

    }
}
