package team4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team4.model.dto.PetitionDto;
import team4.service.MemberService;
import team4.service.PetitionService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/up")


// =======================================================

public class PetitionController {

    @Autowired
    PetitionService petitionService;
    @Autowired private HttpServletRequest request;
    @Autowired
    MemberService memberService;

    @GetMapping("/petition/list")
    public String petitionlist() {
        return "/petition/list";
    }
    @GetMapping("/petition/write")
    public String petitionwrite() {
        return "/petition/write";
    }
    @GetMapping("/petition/view")
    public String PetitionView() {return "/petition/view";}

    @GetMapping("/pt/wr")
    public String petitionWrite() {
        return "/petitionwrite";
    }
    @GetMapping("/pt/li")
    public String petitionList() {
        return "/petitionlist";
    }
    @GetMapping("/pt/vi")
    public String petitionView() {return "/petitionview";}

    // =================================================== //

    // 청원게시글 등록
    @PostMapping("/write")
    @ResponseBody
    public int PetitionWrite(PetitionDto petitionDto){
        System.out.println("PetitionController.PetitionWrite");
        System.out.println("petitionDto = " + petitionDto);
        // 1. 현재 로그인된 세션(톰캣서버(자바프로그램) 메모리(JVM)저장소) 호출
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null){
            return -2;
        }

        // 2. 형변환
        String id = (String) object;

        // 3. id를
        int mno = memberService.doGetLoginInfo(id).getMno();

        // 4. 작성자번호 대입
        petitionDto.setMno(mno);
        return petitionService.PetitionWrite(petitionDto);
    }

    // 청원게시글 출력
    @GetMapping("/list")
    @ResponseBody
    public Object PetitiotionList(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("PetitionController.PetitiotionList");
        return petitionService.PetitiotionList(page,pageSize);
    }

    // 청원게시글 개별 출력
    @GetMapping("/view")
    @ResponseBody
    public PetitionDto PetitionView(@RequestParam int ppno){
        System.out.println("PetitionController.PetitionView");
        System.out.println("ppno = " + ppno);
        return petitionService.PetitionView(ppno);
    }

    // 청원게시글 삭제
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean petitionDelete(@RequestParam int ppno){
        System.out.println("PetitionController.petitionDelete");
        // 유효성검사
        // 1. 현재 로그인된 아이디(세션)
        Object object = request.getSession().getAttribute("loginDto");
        if(object != null){
            String id = (String)object;
            boolean result = petitionService.petitionWriterAuth(ppno, id); // 해당 세션정보가 적상한 글인지 체크
            if(result){
                return petitionService.petitionDelete(ppno);// 2. 현재 삭제할 게시물의 작성자 아이디(DB)
            }
        }
        return false;
    }

    // 청원게시글 동의하기
    @PostMapping("/agree")
    @ResponseBody
    public int petitionAgree(@RequestParam  int ppno ){
        System.out.println("PetitionController.agree");

        // 현재로그인된 아이디
        Object object = request.getSession().getAttribute("loginDto");
            if( object == null )return -1;
            int mno = memberService.doGetLoginInfo( (String)object ).getMno();
        return petitionService.petitionAgree( ppno,mno );
    }

    // 댓글 등록
    @PostMapping("/reply")
    @ResponseBody
    public boolean replyWrite(@RequestParam Map<String, String> map){

        //1. 현재 로그인된 아이디(세션)
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null){return  false;}

        // 2. 형변환
        String id = (String) object;

        // 3. mid를 mno 찾아오기
        int mno = memberService.doGetLoginInfo(id).getMno();

        // 4. map에 mno넣기
        map.put("mno", mno+"");
        System.out.println("mno = " + mno);

        return petitionService.replyWrite(map);
    }

    // 댓글 출력
    @GetMapping("/reply")
    @ResponseBody
    public List<Map<String, Object>> getReply (int ppno){
        System.out.println("PetitionController.getReply");
        System.out.println("ppno = " + ppno);
        return petitionService.getReply(ppno);
    }


} //PetitionController END
