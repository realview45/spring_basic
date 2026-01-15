package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //    get요청의 url의 데이터 추출방식 : pathvariable, 쿼리파라미터
//    case1. pathvariable방식을 통해 사용자로부터 url에서 데이터 추출
//    데이터의 형식 : /memeber/path/1
    @GetMapping("/path/{inputId}")
    @ResponseBody              //Spring에서 형변환해줌 WrapperClass쓴이유 null로 사용하려고구리
    public String path(@PathVariable Long inputId){
//        별도의 형변환 없이도, 원하는 자료형으로 형변환되어 매개변수로 주입. (매개변수의 변수명이 url의 변수명과 일치해야함)
        System.out.println(inputId);
        return "ok";//http문서로 사용자한테 가구리
    }

//    case2.parameter방식을 통한 url에서의 데이터 추출(주로, 검색, 정렬 요청등의 상황에서 사용)
//    case2-1)1개의 파라미터에서 데이터 추출
//    데이터형식 : member/param1?name=hongildong
    @GetMapping("/param1")
    @ResponseBody
    public String param1(@RequestParam(value = "name")String inputName) {
        System.out.println(inputName);
        return "OK";
    }


    //    case2-2)2개의 파라미터에서 데이터 추출
//    데이터형식 : member/param1?name=hongildong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name")String inputName,
                         @RequestParam(value = "email")String inputEmail) {
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "OK";
    }
    //    case2-3)파라미터의 개수가 많아질경우, ModelAttribute를 통한 데이터바인딩
//    데이터바인딩은 param의 데이터를모아 객체로 자동 매핑 및 생성
//    데이터형식 : member/param1?name=hongildong&email=hong@naver.com
    @GetMapping("/param3")
    @ResponseBody       //객체까지 만들어주구리
//    @ModelAttribute는 생략가능
    public String param3(@ModelAttribute Member member) {
        System.out.println(member);
        return "OK";
    }
//    post요청 처리 case : urlencoded, multipart-formdata
//    case1. body의 content-type이 urlencoded형식
//    형식 : body부에 name=hongildong&email=hong@naver.com
    @PostMapping("/url-encoded")
    @ResponseBody
//    형식이 url의 파라미터방식과 동일하므로, RequestParam 또는 데이터바인딩 가능
    public String urlEncoded(@ModelAttribute Member member){
        System.out.println(member);
        return "ok";
    }

}
