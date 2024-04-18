package team4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team4.model.dao.EduDao;
import team4.model.dto.*;
import team4.service.EduService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/up")
public class EduController {
    @Autowired
    private EduDao eduDao;
    @Autowired
    private EduService eduService;

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ PAGE 호출 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
    // === 강의등록
    @GetMapping("/cl")
    public String addClass(){
        return "/add/addclass";
    }
    @GetMapping("/vcl")
    public String viewClass(){
        return "/view/viewclass";
    }
    // === 강의실등록
    @GetMapping("/ro")
    public String addClassRoom(){
        return "/add/addclassroom";
    }
    @GetMapping("/vro")
    public String viewClassRoom(){
        return "/view/viewclassroom";
    }
    // === 강의시간등록
    @GetMapping("/ti")
    public String addClassTime(){
        return "/add/addclasstime";
    }
    @GetMapping("/vti")
    public String viewClassTime(){
        return "/view/viewclasstime";
    }
    // === 교수등록
    @GetMapping("/pf")
    public String addProfessor(){
        return "/add/addprofessor";
    }
    @GetMapping("/vpf")
    public String viewProfessor(){
        return "/view/viewprofessor";
    }
    // === 수강등록
    @GetMapping("/cif")
    public String classInfo(){
        return "/add/addclassinfo";
    }
    @GetMapping("/vcif")
    public String viewClassInfo(){
        return "/view/viewclassinfo";
    }
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

    // 강의실 등록
    @PostMapping("/roomsignup")
    @ResponseBody
    public int roomSignUp(EduDto eduDto){
        System.out.println("EduController.classWrite");
        return eduService.roomSignUp(eduDto);
    }
    // 강의실 수정
    @PutMapping("/roomupdate")
    @ResponseBody
    public int roomUpdate(EduDto eduDto, @RequestParam int rno){
        System.out.println("EduController.classUpdate");
        System.out.println("eduDto = " + eduDto);
        return eduService.roomUpdate(eduDto, rno);
    }
    // 강의실 출력
    @GetMapping("/roominfo")
    @ResponseBody
    public Object roomInfo(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("EduController.roomInfo");
        return eduService.roomInfo(page, pageSize);
    }
    @GetMapping("/rif")
    @ResponseBody
    public List<EduDto> roomInfoList(){
        System.out.println("EduController.roomInfo");
        return eduService.roomInfoList();
    }

    // 강의실 호수 중복체크
    @GetMapping("/roomnumcheck")
    @ResponseBody
    public boolean roomNumCheck(int roomnumber){
        System.out.println("EduController.checkClassRoom");
        return eduService.roomNumCheck(roomnumber);
    }


// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //


    //강의시간 등록
    @PostMapping("/puttime")
    @ResponseBody
    public int putEduTime(ClassTimeDto classTimeDto) {
        System.out.println("EduController.putEduTime");
        System.out.println("classTimeDto = " + classTimeDto);
        return eduService.putEduTime(classTimeDto);
    }

    //강의시간 수정
    @PutMapping("/updatetime")
    @ResponseBody
    public int updateEduTime(ClassTimeDto classTimeDto, @RequestParam int tno) {
        System.out.println("EduController.updateEduTime");
        System.out.println("tno = " + tno);

        return eduService.updateEduTime(classTimeDto, tno);
    }

    //강의시간 중복 확인 요청
    @GetMapping("/checktime")
    @ResponseBody
    public boolean checkEduTime(ClassTimeDto classTimeDto) {
        System.out.println("EduController.checkEduTime");
        System.out.println("classTimeDto = " + classTimeDto);
        return eduService.checkEduTime(classTimeDto);
    }

    //강의시간 출력
    @GetMapping("/classtime")
    @ResponseBody
    public Object classTime(@RequestParam int page, @RequestParam int pageSize) {
        System.out.println("EduController.classTime");
        return eduService.classTime(page,pageSize);
    }

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
    // ====================================== 강의 등록 ====================================== //
    @PostMapping("/classregi")
    @ResponseBody
    public int doPostClass(String classname, String classtype) {
        System.out.println("EduController.doPostClass");
        System.out.println("classname = " + classname + ", classtype = " + classtype);
        return eduService.doPostClass(classname, classtype);
    }

    // ====================================== 강의 수정 ====================================== //
    @PutMapping("/classupdate")
    @ResponseBody
    public int doPutClass(String classname, String classtype, int classno) {
        System.out.println("EduController.doPutClass");
        System.out.println("classname = " + classname + ", classtype = " + classtype + ", classno = " + classno);
        return eduService.doPutClass(classname, classtype, classno);
    }
    // ====================================== 강의 출력 ====================================== //
    @GetMapping("/classinfo")
    @ResponseBody
    public Object doPutClass(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("EduController.doPutClass");
        return eduService.getClassInfo(page,pageSize);
    }
    @GetMapping("/gcl")
    @ResponseBody
    public List<EduInfoDto> getAllClass(){
        return eduService.getAllClass();
    }
    // ====================================== 강의명 중복검사 ====================================== //
    @GetMapping("/checkclassname")
    @ResponseBody
    public boolean checkClassName(String classname){
        System.out.println("EduController.checkClassName");
        return eduService.checkClassName(classname);
    }

    // ====================================== 교수 등록 ====================================== //
    @PostMapping("/createprofessor")
    @ResponseBody
    public boolean createProfessor(ProfessorDto professorDto){
        System.out.println("EduController.createProfessor");
        System.out.println("professorDto = " + professorDto);
        return eduService.createProfessor(professorDto);
    }
    // ====================================== 교수 수정 ====================================== //
    @PutMapping("/putprofessor")
    @ResponseBody
    public int doPutProfessor(ProfessorDto professorDto){
        System.out.println("EduController.doPutProfessor");
        System.out.println("professorDto = " + professorDto);
        return eduService.doPutProfessor(professorDto);
    }

    // ====================================== 교수 출력 ====================================== //
    @GetMapping("/getallprofessor")
    @ResponseBody
    public Object getAllProfessor(@RequestParam int page, @RequestParam int pageSize){
        System.out.println("EduController.getProfessor");
        return eduService.getAllProfessor(page,pageSize);
    }
    @GetMapping("/galp")
    @ResponseBody
    public List<ProfessorDto> getProfessorName(){
        System.out.println("EduController.getProfessor");
        return eduService.getProfessorName();
    }
    // ====================================== 전체회원 출력 [ 회원 중에 교수로 등록하려고 ] ====================================== //
    @GetMapping("/viewmember")
    @ResponseBody
    public List<MemberDto> viewMember(){
        System.out.println("EduController.viewmember");
        return eduService.viewMember();
    }
    // ====================================== 교수 수정하기위해 교수정보 요청 ====================================== //
    @GetMapping("/getoneprofessor")
    @ResponseBody
    public ProfessorDto getOneProfessor(@RequestParam int pno){
        System.out.println("EduController.getOneProfessor");
        return eduService.getOneProfessor(pno);
    }

    // ====================================== 수강 등록 ====================================== //
    @PostMapping("/createallinfo")
    @ResponseBody
    public boolean createAllInfo(@RequestParam Map<String, String> map){
        System.out.println("EduController.createAllInfo");
        System.out.println(map);
        System.out.println(map.get("classno"));
        return eduService.createAllInfo(map);
    }

    // ====================================== 수강 출력 ====================================== //
    @GetMapping("/getallclassinfo")
    @ResponseBody
    public Object getAllClassInfo(@RequestParam int page , @RequestParam int pageSize){
        System.out.println("EduController.getAllClassInfo");
        System.out.println("page +pageSize = " + page +pageSize);
        return eduService.getAllClassInfo(page,pageSize);
    }

    // ====================================== 강의시간의 classno가 null 인것만 요청 ====================================== //
    @GetMapping("/classtimeisnull")
    @ResponseBody
    public List<ClassTimeDto> classTimeIsNull(){
        System.out.println("EduController.classTimeIsNull");
        return eduService.classTimeIsNull();
    }
    // ====================================== 학기 출력 ====================================== //
    @GetMapping("/getseason")
    @ResponseBody
    public List<SeasonDto> getSeason(){
        System.out.println("EduController.getSeason");
        return eduService.getSeason();
    }

    // ====================================== 수강 등록함과 동시에 강의 시간과 강의명 업데이트 ====================================== //
    @PutMapping("/inclassandtime")
    @ResponseBody
    public boolean inClassAndTime(int classno, int tno){
        return eduService.inClassAndTime(classno, tno);
    }

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
} // end