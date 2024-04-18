package team4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.service.MemberService;
import team4.service.PetitionReplyService;
import team4.service.PetitionService;

import java.util.List;
import java.util.Map;

@Controller
public class PetitionReplyController {

    @Autowired
    PetitionReplyService petitionReplyServiceService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;
    //댓글 작성 : replycontent ppno pposition(댓글 위치 0 : 상위 1~ : 하위), mno )
    @PostMapping("/reply/write")
    @ResponseBody
    public boolean postReplyWrite(@RequestParam Map<String,String> map){
        System.out.println("PetitionReplyController.postReplyWrite");

        Object object = request.getSession().getAttribute("loginDto");
        String id = (String) object;

        int mno = memberService.doGetLoginInfo(id).getMno();
        map.put("mno",mno+"");

        return petitionReplyServiceService.postReplyWrite(map);
    }
    //댓글 출력 ( replyno , replycontent , pposition, mno )
    @GetMapping("/reply/do")
    @ResponseBody
    public List<Map<String, Object>>getReplyDo(int ppno){
        System.out.println("PetitionReplyController.getReplyDo");
        return petitionReplyServiceService.getReplyDo(ppno);

    }
}
