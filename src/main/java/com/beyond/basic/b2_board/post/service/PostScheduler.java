package com.beyond.basic.b2_board.post.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostScheduler {
//    fixedDelay를 통해 간단히 주기적인 작업 수행
    @Scheduled(fixedDelay = 1000)//1초마다 실행
    public void simpleScheduler(){
        log.info("====스케줄러 시작=====");

        log.info("====스케줄러 로직수행=====");
        log.info("====스케줄러 끝=======");
    }
}
