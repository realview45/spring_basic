package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Controller 어노테이션을 통해 스프링에 의해 객체가 생성되고, 관리되어 개발자가 직접 객체를 생성할 필요없음.
//Controller 어노테이션과 url매핑을 통해 사용자의 요청이 메서드로 분기처리
@Controller
@RequestMapping("/member")
public class MemberController {
//    get요청 return의 case : text, json, html(mvc)
//    case1. 서버가 사용자에게 text데이터 return
    //사용자가 /member/info/1를 땅치면 메서드를 실행구리
    @GetMapping("")
//    data(text, json)를 리턴할때에는 responsebody 사용. 화면(html)을 리턴할 때에는 responsebody생략.
//    controller+responsebody=restcontroller
    @ResponseBody//메서드명은 가독성을위한작명이구리 사용자맨앞단Controller특수취급구리
    public String textDataReturn(){
        return "hongildong1";
    }
//    case2.서버가 사용자에게 json형식의 문자데이터를 return
    @GetMapping("/json")
    @ResponseBody
    public Member jsonDataReturn() throws JsonProcessingException {
        Member m1 = new Member("h1", "h1@naver.com");
//        직접 json을 직렬화할필요없이, return타입에 객체가 있다면 자동으로 직렬화
//        ObjectMapper o1 = new ObjectMapper();
//        String data = o1.writeValueAsString(m1);
        return m1;
    }
//    case3. 서버가 사용자에게 html return (잘안씀 rest api를 지향)
//    case3-1)정적인 html return
    @GetMapping("/html")
//    ResponseBody가 없고,
//    return타입이 String인 경우, 스프링은 templates폴더 밑에 simple_html.html을 찾아서 리턴.
//    타임리프 의존성이 필요.
    public String htmlReturn(){
        return "simple_html";
    }


    //    case3. 서버가 사용자에게 html return (잘안씀 rest api를 지향)
//    case3-2)서버에서 화면+데이터를 함께 주는 동적인화면
//    현재 이 방식은 ssr(서버사이드렌더링). csr방식은 화면은 별도제공하고, 서버는 데이터만 제공.
    @GetMapping("/html/dynamic")
//    get요청의 url의 데이터 추출방식 : pathvariable, 쿼리파라미터
//    post요청의 처리case : urlencoded, multipart-formdata, json
    public String dynamicHtmlReturn(Model model){
        // model객체는 데이터를 화면에 전달해주는 역할
        // name=hongildong형태로 화면에 전달
        model.addAttribute("name", "hongildong");
        model.addAttribute("email", "hongildong@naver.com");
        return "dynamic_html";
    }
}
