package com.beyond.basic.b2_board.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
//    logback 객체를 만드는 방법1.
    private static Logger logger = LoggerFactory.getLogger(LogController.class);


    @GetMapping("/log/test")
    public String logTest(){
//        system println의 문제점
//        1)출력의 성능이 떨어짐 2)로그분류작업 불가
        System.out.println("hello world");
//        가장 많이 사용되는 로그라이브러리 : logback
        logger.trace("trace로그입니다.");
        logger.debug("debug로그입니다.");
        logger.info("info로그입니다.");
        logger.error("error로그입니다.");
        logger.info("hello world");

        return "OK";
    }
}
