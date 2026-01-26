package com.beyond.basic.b2_board.common.init;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.domain.Role;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//CommandLineRunner를 구현함으로서 아래 run메서드가 스프링빈으로 등록되는 시점에 자동실행
@Component
@Transactional//쿼리를 날리는 부분구리 CommandLineRunner는 스프링 애플리케이션이 기동된 직후 실행시키고 싶은 코드가 있다면 사용구리
public class InitialDataLoad implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public InitialDataLoad(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(authorRepository.findByEmail("admin@naver.com").isPresent()){
            return;
        }
        authorRepository.save(Author.builder()
                .name("admin").email("admin@naver.com").role(Role.ADMIN).password(passwordEncoder.encode("12341234"))
                .build());
    }
}
