package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.service.AuthorService;
import com.beyond.basic.b2_board.common.auth.JwtTokenProvider;
import com.beyond.basic.b2_board.common.dtos.CommonErrorDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//AuthorController
//1.회원가입 Post
//    url : /author/create
//    형식 : json
//2.회원목록조회 Get
//    url : /author/list
//    형식 : json, List<Author> [{},{},{}]
//3.회원상세조회 : ID조회Get
//    url : /author/
//    형식 :
//4.회원탈퇴 : ID삭제 DeleteMapping
//    url : /author/create
//    형식 : json
@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthorController(AuthorService authorService, JwtTokenProvider jwtTokenProvider){
        this.authorService = authorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Autowired
//    private AuthorService authorService;
//    public AuthorController(){
//        authorService ';= new AuthorService();
//    }
    @PostMapping("/create")
//    dto에 있는 validation어노테이션과 @Valid가 한쌍
    public ResponseEntity<?> create(@RequestBody @Valid AuthorCreateDto dto){
//        아래 예외처리는 ExceptionHandler에서 전역적으로 예외처리
//        try {
//            authorService.save(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
//        }
//        catch(IllegalArgumentException e){
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = CommonErrorDto.builder()
//                    .status_code(400)
//                    .error_message(e.getMessage())
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonErrorDto);
//        }
        authorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        if(dtoList.isEmpty()){

        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            AuthorDetailDto dto = authorService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            CommonErrorDto dto = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }
//    아래와 같이 http응답 body를 분기처리한다하더라도 상태코드는 200으로 고정
//    @GetMapping("/{id}")
//    public Object findById(@PathVariable Long id){
//        AuthorDetailDto dto = null;
//        try {
//            dto = authorService.findById(id);
//            return dto;
//        }catch(NoSuchElementException e){
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return CommonErrorDto.builder()
//                    .status_code(404)
//                    .error_message(e.getMessage())
//                    .build();
//        }
//    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        authorService.delete(id);
        return "ok";
    }

    @PatchMapping("/update/password")
    public void updatePw(@RequestBody AuthorUpdatePwDto dto){
        authorService.updatePw(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthorLoginDto dto){
        Author author = authorService.login(dto);
//        토큰 생성 및 리턴
        String token = jwtTokenProvider.createToken(author);
        return token;
    }
}
