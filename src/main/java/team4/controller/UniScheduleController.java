package team4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team4.model.dto.UniScheduleDto;
import team4.service.UniScheduleService;

import java.util.List;

@Controller
@RequestMapping("/up/sch")
public class UniScheduleController {

    @Autowired
    UniScheduleService uniScheduleService;

    //학사 일정 등록
    @PostMapping("/write.do")
    @ResponseBody
    public boolean doSchedulWrite(UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleController.doSchedulWrite");
        System.out.println(uniScheduleDto);
        return uniScheduleService.doSchedulWrite(uniScheduleDto);
    }

    //학사 일정 출력
    @GetMapping("/info")
    @ResponseBody
    public Object getSchedul(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("UniScheduleController.getSchedul");

        return uniScheduleService.getSchedul(page,pageSize);
    }

    //학사 일정 수정 전 정보
    @GetMapping("/updateInfo")
    @ResponseBody
    public List<UniScheduleDto> getSchedulUpdateInfo(@RequestParam int scno){
        System.out.println("UniScheduleController.getSchedulUpdateInfo");
        return uniScheduleService.getSchedulUpdateInfo(scno);
    }

    //학사 일정 수정
    @PutMapping("/update")
    @ResponseBody
    public boolean doSchedulUpdate(@RequestParam int scno, UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleController.doSchedulUpdate");
        return uniScheduleService.doSchedulUpdate(scno, uniScheduleDto);
    }

    //학사 일정 삭제
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean doSchedulDelete(@RequestParam int scno){
        System.out.println("UniScheduleController.doSchedulDelete");
        return uniScheduleService.doSchedulDelete(scno);
    }

    //================ 화면 실행 ====================
    @GetMapping("/do")
    public String schedulView(){
        return "/schedul";
    }

    @GetMapping("/view")
    public String schedulCalendarView(){
        return "/schedulCalendar";
    }

    @GetMapping("/cal.do")
    public String postSchedul(){
        return "/schedul";
    }

    @GetMapping("/cal")
    public String viewCalendar(){
        return "/calendar";
    }


}
