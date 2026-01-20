package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import com.beyond.basic.b2_board.common.CommonErrorDto;
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
    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

//    @Autowired
//    private AuthorService authorService;
//    public AuthorController(){
//        authorService ';= new AuthorService();
//    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuthorCreateDto dto){
        try {
            authorService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            CommonErrorDto commonErrorDto = CommonErrorDto.builder()
                    .status_code(400)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonErrorDto);
        }
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
}
