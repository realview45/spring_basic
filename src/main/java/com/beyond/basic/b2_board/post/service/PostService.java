package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }
    public void save(PostCreateDto dto) {
//        Author author = authorRepository.findByEmail(dto.getAuthorEmail()).orElseThrow(()->new EntityNotFoundException("email is not found"));
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(email);
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("email is not found"));

        postRepository.save(dto.toEntity(author));
    }
    @Transactional(readOnly=true)
    public PostDetailDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("entity is not found"));
//        Author author = authorRepository.findById(post.getAuthorId()).orElseThrow(()->new EntityNotFoundException("entity is not found"));
//        PostDetailDto postDetailDto = PostDetailDto.fromEntity(post, author);
        PostDetailDto postDetailDto = PostDetailDto.fromEntity(post);
        return postDetailDto;
    }
    @Transactional(readOnly=true)
    public List<PostListDto> findAll() {
        List<Post> postList = postRepository.findAllByDelYn("N");
        List<PostListDto> dtoList = new ArrayList<>();
        for(Post p : postList){
            dtoList.add(PostListDto.fromEntity(p));
        }
        return dtoList;
//        return postList.stream().map(p->
//                PostListDto.fromEntity(p,authorRepository
//                        .findById(p.getAuthorId()).orElseThrow(()-> new EntityNotFoundException("entity not found"))))
//                .collect(Collectors.toList());
    }
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity is not found"));
        if(post.getDelYn().equals("Y")){
            throw new EntityNotFoundException("Entity is not found");
        }
        post.deleteDelYn();
    }
}
