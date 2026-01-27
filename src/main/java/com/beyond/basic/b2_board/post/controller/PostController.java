package com.beyond.basic.b2_board.post.controller;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.service.PostService;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid PostCreateDto dto){
        postService.save(dto);
    }

    @GetMapping("/post/{id}")
    public PostDetailDto findById(@PathVariable Long id){
        PostDetailDto dto = postService.findById(id);
        return dto;
    }

    @GetMapping("/posts")//Pageable객체를 받겠다 RequestParam과 비슷
    public Page<PostListDto> findAll(Pageable pageable){
        return postService.findAll(pageable);
    }
//    @GetMapping("/posts")
//    public List<PostListDto> findAll(){
//        List<PostListDto> postList = postService.findAll();
//        return postList;
//    }

    @PatchMapping("/post/{id}")
    public void delete(@PathVariable Long id){
        postService.delete(id);
    }
}
