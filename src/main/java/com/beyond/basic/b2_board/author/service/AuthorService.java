package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
//Component어노테이션을 통해 싱글통(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
//3반드시 초기화되어야하는필드(final변수 등)를 대상으로 생성자를 자동생성
@RequiredArgsConstructor
public class AuthorService {
//    의존성주입(DI)방법1. 필드주입 : Autowired어노테이션사용 (간편방식)
//    @Autowired
////    의존성주입(DI)방법2. 생성자주입방식(가장많이 사용되는 방식)
////    장점1)final을 통해 상수로 사용가능(안정성향상) new가 안됨 1은 초기화못함
////    장점2)다형성 구현가능(interface사용가능) 1은 자식중에 누구넣을지 모르기때문
////    장점3)순환참조방지(컴파일타임에 에러check)이건 123다된다
//    private final AuthorRepository authorRepository;
//    @Autowired
//    public AuthorService(AuthorRepository authorRepository){
//        this.authorRepository = authorRepository;
//    }
////    public AuthorService(){
////        this.authorRepository = new AuthorRepository();
////    }

//    의존성주입방법3. RequiredArgsConstructor어노테이션 사용방법 장점1가능
//반드시 초기화되어야 하는 필드를 선언하고, 위 어노테이션 선언시 생성자주입방식으로 의존성이 주입됨
//        단점 : 다형성 설계는 불가
    private final AuthorRepository authorRepository;

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
