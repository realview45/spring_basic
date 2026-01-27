package com.beyond.basic.b2_board.author.service;
import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.repository.*;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;
//    생성자가 하나밖에 없을때에는 Autowired생략가능 내가 짤때는 붙여주는게 성능이 좋다라고 알려짐
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository, PasswordEncoder passwordEncoder){
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void save(AuthorCreateDto dto){
        Optional<Author> optAuthor = authorRepository.findByEmail(dto.getEmail());
        if(optAuthor.isPresent()){
            throw new IllegalArgumentException("email중복입니다.");
        }
        Author author = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
//        cascade persist를 활용한 예시
        Author authorDb = authorRepository.save(author);
        author.getPostList().add(Post.builder().title("안녕하세요").author(authorDb).build());//?

//        cascade 옵션이 아닌 예시
//        postRepository.save(Post.builder().title("안녕하세요").author(authorDb).build());
//        예외 발생시 transactional 어노테이션에 의해 rollback처리
        //Transactional이 붙어 한트랜잭션내에 에러발생시 위의 save도 rollback처리된다구리.
//        authorRepository.findById(2L).orElseThrow(()->new NoSuchElementException("entity is not found"));
    }

    public void updatePw(AuthorUpdatePwDto dto){
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new EntityNotFoundException("Entity not found"));
        author.updatePassword(dto.getPassword());
//        insert, update 모두 save메서드 사용 -> 변경감지로 대체
//        authorRepository.save(author);
//        영속성컨텍스트 : 애플리케이션과 DB사이에서 객체를 보관하는 가상의 DB역할
//        1)쓰기지연 : insert, update 등의 작업사항을 즉시 실행하지 않고, 커밋시점에 모아서 실행(성능향상)
//        2)변경감지(dirty checking) : 영속상태(managed)의 엔티티는 트랜잭션 커밋시점에 변경감지를 통해 별도의 save없이 DB에 반영
    }

    @Transactional(readOnly=true)
    public List<AuthorListDto> findAll(){
        return authorRepository.findAll().stream()
                .map(a->AuthorListDto.fromEntity(a))
                .collect(Collectors.toList());//리스트로 바꿔준다.
    }
//    트랜잭션 처리가 필요없는 조회만 있는 메서드의 경우 성능향상을 위해 readOnly처리
    @Transactional(readOnly=true)
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = optAuthor.orElseThrow(()->new NoSuchElementException("entity is not found"));
//        List<Post> postList = postRepository.findAllByAuthorIdAndDelYn(author.getId(), "N");
                                                    //entitynotfound jpa구리
//        return AuthorDetailDto.fromEntity(author, 0);
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }
    public void delete(Long id){
//        데이터 조회 후 없다면 예외처리
        Author author = authorRepository.findById(id).orElseThrow(()->new NoSuchElementException("entity is not found"));
//        삭제작업
        authorRepository.delete(author);
    }
    public Author login(AuthorLoginDto dto){
        Optional<Author> opt_author = authorRepository.findByEmail(dto.getEmail());
        boolean check = true;
        if(!opt_author.isPresent()){
            check=false;
        }else{
            if(!passwordEncoder.matches(dto.getPassword(),opt_author.get().getPassword())){
                check=false;
            }
        }
        if(!check){
            throw new IllegalArgumentException("이메일또는 비밀번호가 일치하지 않습니다.");
        }
        return opt_author.get();
    }
//    @Transactional(readOnly=true)
//    public AuthorDetailDto myinfo(){
//        Author author = authorRepository.findByEmail(email).orElseThrow(()->new NoSuchElementException("entity is not found"));
//        return AuthorDetailDto.fromEntity(author);
//    }

    @Transactional(readOnly=true)
    public AuthorDetailDto myinfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();//외우구리
        Optional<Author> optAuthor = authorRepository.findByEmail(email);
        Author author = optAuthor.orElseThrow(()->new EntityNotFoundException("entity is not found"));
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }
}
