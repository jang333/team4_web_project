package team4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dto.MemberDto;
import team4.service.AuthorizationService;

import java.util.List;


@Controller
public class AuthorizationController {
   @Autowired AuthorizationService authorizationService;



    //권한 부여를 위한 정보 요청
    @GetMapping("/authorization")
    @ResponseBody
    public Object doGetAuthorization(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("AuthorizationController.doGetAuthorization");

        return authorizationService.doGetAuthorization(page,pageSize);
    }

    //권한 변경
    @PutMapping("/changestate")
    @ResponseBody
    public boolean doPutchangestate(int state, MemberDto memberDto){
        System.out.println("AuthorizationController.doPutchangestate");
        return authorizationService.doPutchangestate(state, memberDto);
    }



    //권한 부여를 위한 페이지 요청
    @GetMapping("/member/authorization")
    public String AuthorizationView(){
        return "/authorization";
    }

    @GetMapping("/m/auth")
    public String addAuthorization(){
        return "/add/addauthorization";
    }
}
