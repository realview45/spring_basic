package com.beyond.basic.b2_board.common.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
//Aspect : aop코드임을 명시
@Aspect
@Component
@Slf4j
public class AopLogService {
    //  AOP의 대상이 되는 controller, service 등을 패키지구조(어노테이션) 기준으로 명시
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointCut() {}

//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void controllerPointCut() {}

//    joinpoint는 사용자가 실행하고자 하는 코드를 의미하고, 위에서 정의한 pointcut을 의미
    @Around("controllerPointCut()")
    public Object controllerLogger(ProceedingJoinPoint joinPoint){
//        joinpoint 이전
        log.info("aop start");
        log.info("요청자 : "  + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        log.info("요청 메서드명 : " + joinPoint.getSignature().getName());

        //logback 활용 log 파일분리구리
//        joinpoint 실행
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);//check를 받아서 unchecked를 던진다.
        }

//        joinpoint 이후
        log.info("aop end");

        return object;
    }
}