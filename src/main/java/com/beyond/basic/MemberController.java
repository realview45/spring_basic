package com.beyond.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Controller 어노테이션을 통해 스프링에 의해 객체가 생성되고, 관리되어 개발자가 직접 객체를 생성할 필요없음.
//Controller 어노테이션과 url매핑을 통해 사용자의 요청이 메서드로 분기처리
@Controller
@RequestMapping("/member")
public class MemberController {

    //사용자가 /member/info/1를 땅치면 메서드를 실행구리
    @GetMapping("")
    @ResponseBody
    public String textDataReturn(){
        return "hongildong1";
    }
}
