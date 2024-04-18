package team4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dao.MemberDao;
import team4.model.dto.EnrolmentDto;
import team4.model.dto.EnrolmentlistDto;
import team4.service.EnrolmentService;

import java.util.List;


@Controller
public class EnrolmentController {
    @Autowired
    EnrolmentService enrolmentService;
    @Autowired
    HttpServletRequest request;

// ============================ PAGE 호출 ============================ //
    @GetMapping("/enrolment")
    public String  getEnrolment(){
        return "/Enrolment";
    }
    @GetMapping("/up/enr")
    public String  enrolment(){
        return "/enrolment";
    }
    @GetMapping("/classlist")
    public String getClassList(){
        return "/enrolmentlist";
    }
    @GetMapping("/up/enlist")
    public String enrolmentList(){
        return "/enrolmentlist";
    }
// ======================================================================= //

    @GetMapping("/searchMno")
    @ResponseBody
    public EnrolmentDto searchMno(){
        System.out.println("EnrolmentController.searchMno");
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        System.out.println("sessionObj = " + sessionObj);
        if(sessionObj != null){
            loginDto = (String)sessionObj ;
        }
        EnrolmentDto enrolmentDto = EnrolmentDto.builder()
                .mno(enrolmentService.searchMno(loginDto))
                .build();

        return enrolmentDto;
    }

    @PostMapping("/regist")
    @ResponseBody
    public int postRegist( EnrolmentDto enrolmentDto){
        System.out.println("controller enrolmentDto = " + enrolmentDto);
        return enrolmentService.postRegist(enrolmentDto);
    }

    //개별출력
    @GetMapping("/classlist.do")
    @ResponseBody
    public Object doClassList(@RequestParam int page, @RequestParam int pageSize){
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        System.out.println("sessionObj = " + sessionObj);
        if(sessionObj != null){
            loginDto = (String)sessionObj ;
            System.out.println("loginDto = " + loginDto);
        }
        int mno = enrolmentService.searchMno(loginDto);
        return enrolmentService.getClassList(mno,page,pageSize);
    }
}
