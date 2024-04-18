package team4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.service.EduService;

@Controller
public class pagnationController {
    @Autowired
    EduService eduService;
    @GetMapping("/pagnation")
    @ResponseBody
    public Object pagnation(@RequestParam int pageno, @RequestParam int page, @RequestParam int pageSize){
        System.out.println("pageno" + pageno);
        System.out.println("pagnationController.pagnation");
//        if(pageno == 1 ){ //강의시간 출력
//            return eduService.classTime(page,pageSize);
//        }else if (pageno == 2){ //강의등록 출력
            return eduService.getClassInfo(page,pageSize);
//        }
//        return 0;
    }
}
