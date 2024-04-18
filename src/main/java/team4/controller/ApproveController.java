package team4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dto.EduInfoDto;
import team4.service.ApproveService;

import java.util.List;

@Controller
public class ApproveController {
    @Autowired
    private ApproveService approveService;
    @GetMapping("/approve")
    @ResponseBody
    public List<EduInfoDto> putApprove(){
        System.out.println("ApproveController.putApprove");
        return approveService.putApprove();
    }
    @PutMapping("/changestateclass")
    @ResponseBody
    public boolean putchangeStateClass(String cstate, String cno){
        System.out.println("cstate = " + cstate + ", cno = " + cno);
        return approveService.putchangeStateClass(cstate, cno);
    }
     
    //상태 변경을 위한 페이지 요청
    @GetMapping("/member/approve")
    public String approveView(){
        return "/approve";
    }
}
