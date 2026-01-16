package com.beyond.basic.b2_board.author.repository;
import com.beyond.basic.b2_board.author.domain.Author;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//회원가입 : name, email, password
//회원상세조회 : id, name, email, password 엔티티
//회원목록조회 : id, name, email DTO
public class AuthorRepository {
    private List<Author> authorList = new ArrayList<>();
    private static Long staticId =1L;

    //staticId값 리턴도 가능구리
    public void save(Author author){
//        insert into author(name, email, password) values(author.getName(), author.getEmail(), author.getPasswaord())
        this.authorList.add(author);
//        jpa야 author줄테니까 알아서 insert 따라서 원형이 있어야함
        author.setId(staticId++);
    }
    public List<Author>findAll(){
        return this.authorList;
    }
    public Optional<Author> findById(Long id){
        Author author = null;
        for(Author a : this.authorList){
            if(a.getId().equals(id)){
                author = a;
            }
        }
        return Optional.ofNullable(author);
    }
}
