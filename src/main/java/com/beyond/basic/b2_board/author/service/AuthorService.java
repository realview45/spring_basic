package com.beyond.basic.b2_board.author.service;
import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorJdbcRepository;
import com.beyond.basic.b2_board.author.repository.AuthorJpaRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMemoryRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMybatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
//Component어노테이션을 통해 싱글통(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
//스프링에서 jpa를 활용할때 트랜잭션처리(commit, rollback)지원.
//commit기준점 : 메서드 정상 종료 시점. rollback의 기준점 : 예외발생했을경우.
//Transactional과 Service가 같이붙어있구리
@Transactional
public class AuthorService {
    private final AuthorJpaRepository authorRepository;
//    생성자가 하나밖에 없을때에는 Autowired생략가능 내가 짤때는 붙여주는게 성능이 좋다라고 알려짐
    @Autowired
    public AuthorService(AuthorJpaRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    public void save(AuthorCreateDto dto){
        Optional<Author> optAuthor = authorRepository.findByEmail(dto.getEmail());
        if(optAuthor.isPresent()){
            throw new IllegalArgumentException("email중복입니다.");
        }
        Author author = dto.toEntity();
        authorRepository.save(author);
//        예외 발생시 transactional 어노테이션에 의해 rollback처리
        //Transactional이 붙어 한트랜잭션내에 에러발생시 위의 save도 rollback처리된다구리.
//        authorRepository.findById(2L).orElseThrow(()->new NoSuchElementException("entity is not found"));
    }
    public List<AuthorListDto> findAll(){
        return authorRepository.findAll().stream()
                .map(a->AuthorListDto.fromEntity(a))
                .collect(Collectors.toList());//리스트로 바꿔준다.
    }
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
                                                    //entitynotfound jpa구리
        Author author = optAuthor.orElseThrow(()->new NoSuchElementException("entity is not found"));
        return AuthorDetailDto.fromEntity(author);
    }
    public void delete(Long id){
//        데이터 조회 후 없다면 예외처리
        Author author = authorRepository.findById(id).orElseThrow(()->new NoSuchElementException("entity is not found"));
//        삭제작업
        authorRepository.delete(id);
    }
}
