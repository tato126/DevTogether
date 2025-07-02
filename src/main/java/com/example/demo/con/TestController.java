package com.example.demo.con;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

    // 뭘 해야 하지?
    // 스프링부트로 글을 등록하는 기능을 만들어보자
    // 그러려면 그림이 어떻게 만들어져야 하지?
    // 그림을 그려보자
    // 흐름은 다음과 같다. 메인화면 -> 글 등록 버튼 -> 게시글 작성 -> 작성글 등록 -> 메인화면
    // 이 부분을 테스트 해보자
    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    // 메인페이지 접속
    // 글 등록 버튼이 있는지 확인
    // 글 등록 버튼 클릭시 게시글 작성 페이지까지 이동해야한다. 성공!
    @GetMapping("/main")
    public String mainPage() {
        return "Main";
    }

    // 글 등록 버튼을 클릭한 후에 작성 페이지로 이동하는 컨트롤러 -> 성공!
    @GetMapping("/test/write")
    public String write() {
        return "Write";
    }

    // 상세 페이지에서 글 등록시 메인 페이지로 돌아와야한다.
    // post를 사용하여 만들어보자 -> 왜냐면 클라이언트에서 서버에 데이터를 보내야하니까
    // 흐름은 테스트 성공했다.
    @PostMapping("/test/create")
    public String create() {
        return "redirect:/main"; // 리다이렉트가 무엇인가요? -> 주소를 반환한다? (ex: GetMapping : /main)
    }

    // 이제 뭘 해야하지?
    // 실제로 글을 등록해볼까?
    // 실제로 글을 등록하려면 어떻게 해야하지?
    // 데이터 베이스를 일단 연결해볼까?
    // 데이터 베이스는 mysql을 사용해보자
    // 먼저 데이터 베이스부터 연결해보자
    // db 의존성 추가 -> repository -> 메인 실행시 동작 까지 테스트해보자
    // 스프링부트와 DB 연결 간에 에러가 발생했다.
    // 에러 내용은 - defined in class path resource -> Cannot load driver class: com.mysql.cj.jdbc.Driver
    // 해결 :  runtimeOnly 'com.mysql:mysql-connector-j' 의존성이 추가되어야 한다.

    // 글 등록시 실제로 DB에 등록해보자
    // 게시글 작성 상세 페이지에서 글을 작성하면 데이터 베이스에 저장되어야 한다.
    // 필요한 것 Entity , DTo
    // Entity를 먼저 작성해보자 entity 멤버는 title 과 content 이다.
    // 리포지토리 인터페이스 작성
}
