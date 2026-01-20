package com.beyond.basic.b2_board.author.service;
import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorJdbcRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
//Component어노테이션을 통해 싱글통(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
public class AuthorService {
    private final AuthorJdbcRepository authorRepository;
//    생성자가 하나밖에 없을때에는 Autowired생략가능 내가 짤때는 붙여주는게 성능이 좋다라고 알려짐
    @Autowired
    public AuthorService(AuthorJdbcRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void save(AuthorCreateDto dto){
        authorRepository.save(dto.toEntity());
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
