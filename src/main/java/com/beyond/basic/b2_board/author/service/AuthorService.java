package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
//Component어노테이션을 통해 싱글통(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
public class AuthorService {
//    의존성주입(DI)방법1. 필드주입 : Autowired어노테이션사용 (간편방식)
    @Autowired
    private AuthorRepository authorRepository;
//    의존성주입(DI)방법2. 생성자주입방식

//    public AuthorService(){
//        this.authorRepository = new AuthorRepository();
//    }
    public void save(AuthorCreateDto dto){
//        객체 직접 조립
        Author author = new Author(null, dto.getName(), dto.getEmail(), dto.getPassword());
        authorRepository.save(author);
    }
    public List<AuthorListDto> findAll(){
        List<Author> authorList = authorRepository.findAll();
        List<AuthorListDto> dtoList = new ArrayList<>();
        for(Author a : authorList){
            dtoList.add(new AuthorListDto(a.getId(),a.getName(),a.getEmail()));
        }
        return dtoList;
    }
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
                                                    //entitynotfound jpa구리
        Author author = optAuthor.orElseThrow(()->new NoSuchElementException("entity is not found"));
        //        dto조립
        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getEmail(), author.getPassword());
        return dto;
    }
}
