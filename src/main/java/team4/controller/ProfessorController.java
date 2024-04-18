package team4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dto.EduInfoDto;
import team4.model.dto.ProfessorDto;
import team4.service.MemberService;
import team4.service.ProfessorService;

import java.util.List;

@Controller
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;
    //배정된 강의 보기
    @GetMapping("/member/professor/class")
    @ResponseBody
    public List<EduInfoDto> doGetProfessorClass(){
        System.out.println("ProfessorController.doGetProfessorClass");
        String loginDto = null;
        int mno = 0;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        if(sessionObj != null){
            loginDto = (String)sessionObj ;
            mno = memberService.doGetLoginInfo(loginDto).getMno();
        }
        System.out.println(mno);
        return professorService.doGetProfessorClass(mno);
    }
    @GetMapping("member/pfclass")
    public String pfclassView(){
        return "/professorView";
    }

}
