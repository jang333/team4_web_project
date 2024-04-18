package team4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team4.model.dao.MemberDao;
import team4.model.dto.LoginDto;
import team4.model.dto.MemberDto;
import team4.service.MemberService;

@Controller

public class MemberController {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpServletRequest request;
    //회원가입 요청하기
    @PostMapping("/member/signup") //localhost:80/member/signup
    @ResponseBody
    public boolean doPostSignup(MemberDto memberDto){
        return memberService.doPostSignup(memberDto);
    }

    //로그인 처리 요청하기
    @PostMapping("/member/login")
    @ResponseBody
    public boolean doPostLogin(LoginDto loginDto){
        boolean result = memberDao.doPostLogin(loginDto);
        if(result){
            request.getSession().setAttribute("loginDto",loginDto.getId());
        }
        return result;
    }

    //로그인 여부 확인 (세션에 집어넣기)
    @GetMapping("/member/login/check")
    @ResponseBody
    public String dogetLoginCheck(){
        System.out.println("MemberController.dogetLoginCheck");
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        if(sessionObj != null){
            loginDto = (String)sessionObj;
        }
        return loginDto;
    }
    //로그아웃 & 세션 초기화
    @GetMapping("/member/logout")
    @ResponseBody
    public boolean doGetLogOut(){
        request.getSession().invalidate();
        return true;
    }
    //로그인된 회원 정보 요청
    @GetMapping("/member/login/info")
    @ResponseBody
    public MemberDto doGetLoginInfo(String id){
        return memberService.doGetLoginInfo(id);
    }
    //아이디 중복 검사
    @GetMapping("/member/find/idcheck")
    @ResponseBody
    public boolean doGetFindIdCheck(@RequestParam String id){
        return memberService.doGetFindIdCheck(id);
    }



//====================================================================
    //회원가입 페이지 요청
    @GetMapping("/member/signup")
    public String viewSignup(){
        System.out.println("MemberController.viewSignup");
        return "/signup";
    }
    //로그인 페이지 요청
    @GetMapping("/member/login")
    public String viewLogin(){
        System.out.println("MemberController.viewLogin");
        return "/login";
    }
    // 로그인 이후 페이지 요청
    @GetMapping("/up")
    public String afterLogin(){
        System.out.println("MemberController.afterLogin");
        return "/afterlogin";
    }

    @GetMapping("/m/sig")
    public String testviewSignup(){
        return "/signup";
    }
    //로그인 페이지 요청
    @GetMapping("/m/log")
    public String testviewLogin(){
        return "/login";
    }
    // 로그인 이후 페이지 요청
    @GetMapping("/in")
    public String testafterLogin(){
        return "/afterlogin";
    }
}
