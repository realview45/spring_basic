package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String create(@RequestBody AuthorCreateDto dto){
        authorService.save(dto);
        System.out.println(dto);
        return "ok";
    }

    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        if(dtoList.isEmpty()){

        }
        return dtoList;
    }

    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable Long id){
        AuthorDetailDto dto = null;
        try {
            dto = authorService.findById(id);
        }catch(NumberFormatException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return dto;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        authorService.delete(id);
        return "ok";
    }
}
