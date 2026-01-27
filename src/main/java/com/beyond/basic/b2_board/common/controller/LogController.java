package com.beyond.basic.b2_board.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    @GetMapping("/log/test")
    public String logTest(){
//        system println의 문제점
//        1)출력의 성능이 떨어짐 2)로그분류작업 불가
        System.out.println("hello world");
        return "OK";
    }
}
