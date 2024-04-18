package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import team4.model.dao.EduDao;
import team4.model.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EduService {
    @Autowired
    private EduDao eduDao;

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
// ==================================== 강의실 배정 ======================================= //
    // 강의실 등록
    public int roomSignUp(EduDto eduDto){
        System.out.println("EduController.classWrite");
        return eduDao.roomSignUp(eduDto);
    }

    // 강의실 수정
    public int roomUpdate(EduDto eduDto, @RequestParam int rno){
        System.out.println("EduController.classUpdate");
        return eduDao.roomUpdate(eduDto, rno);
    }

    // 강의실 출력
    public Object roomInfo(int page, int pageSize){
        System.out.println("EduController.roomInfo");
        int startRow = (page-1)*pageSize;

        //총게시물수
        int totalSize = eduDao.roomInfototal();

        //총페이지수
        int totalPage = totalSize%pageSize == 0?
                        totalSize/pageSize :
                        totalSize/pageSize+1;

        List<Object> list = eduDao.roomInfo(startRow, pageSize);

        int btnSize = 5;
        int startBtn = (page-1)/btnSize*btnSize+1;;
        int endBtn = startBtn + btnSize - 1;
        if(endBtn >= totalPage) endBtn = totalPage;

        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalPage)
                .totalSize(totalSize)
                .endbtn(endBtn)
                .startbtn(startBtn)
                .list(list)
                .build();
        return object;
    }

    public List<EduDto> roomInfoList(){
        System.out.println("EduController.roomInfo");
        return eduDao.roomInfoList();
    }

    // 강의실 유효성, 중복체크
    public boolean roomNumCheck(int roomnumber) {
        System.out.println("EduController.checkClassRoom");
        return eduDao.roomNumCheck(roomnumber);
    }
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
// ====================================== 강의 시간 ====================================== //
    //강의시간 등록
    public int putEduTime(ClassTimeDto classTimeDto){
        System.out.println("EduService.putEduTime");
        System.out.println("classTimeDto = " + classTimeDto);
        return eduDao.putEduTime(classTimeDto);
    }

    //강의시간 수정
    public int updateEduTime(ClassTimeDto classTimeDto, int tno){
        System.out.println("EduService.updateEduTime");
        return eduDao.updateEduTime(classTimeDto, tno);
    }

    //강의시간 중복 확인 요청
    public boolean checkEduTime(ClassTimeDto classTimeDto){
        System.out.println("EduService.checkEduTime");
        return eduDao.checkEduTime(classTimeDto);
    }

    //강의시간출력
    public Object classTime(int page,int pageSize ){
        System.out.println("EduService.classTime");

        pageSize = 5; //한페이지에 들어갈 게시물 수

        int totalPageSize = eduDao.getTotalPage(); //등록된 강의시간 전체 수
        System.out.println("totalPageSize = " + totalPageSize);

        int startRow = (page-1)*pageSize;

        //전체페이지수
        int totalPage = totalPageSize%pageSize == 0 ?
                        totalPageSize/pageSize :
                        totalPageSize/pageSize+1;

        List<Object> list = eduDao.classTime(startRow,pageSize);

        int btnSize = 5;
        int startBtn = (page-1)/btnSize*btnSize+1;;
        int endBtn = startBtn + btnSize - 1;
        if(endBtn >= totalPage) endBtn = totalPage;


        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalPage)
                .totalSize(totalPageSize)
                .startbtn(startBtn)
                .endbtn(endBtn)
                .list(list)
                .build();


        return object;
    }



// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
// ====================================== 강의 등록 ====================================== //
    public int doPostClass(String classname, String classtype){
        System.out.println("EduService.doPostClass");
        System.out.println("classname = " + classname + ", classtype = " + classtype);
        return eduDao.doPostClass(classname,classtype);
    }
// ====================================== 강의 수정 ====================================== //
    public int doPutClass(String classname, String classtype, int classno){
        System.out.println("EduController.doPutClass");
        System.out.println("classname = " + classname + ", classtype = " + classtype + ", classno = " + classno);
        return eduDao.doPutClass(classname, classtype, classno);
    }
// ====================================== 강의 출력 ====================================== //
    public Object getClassInfo(int page, int pageSize){
        System.out.println("page = " + page);
        System.out.println("EduService.doPutClass");

        int startRow = (page-1)*pageSize;

        //총게시물수
        int totalSize = eduDao.getClassTotalpage();
        System.out.println("totalClassTimeSize = " + totalSize);

        int totalpage = totalSize%pageSize == 0 ?
                totalSize/pageSize :
                totalSize/pageSize +1;

        int btnSize = 5;
        int startbtn = (page-1)/btnSize*btnSize+1;;
        int endbtn = startbtn + btnSize - 1;
        if(endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = eduDao.getClassInfo(startRow,pageSize);



        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalpage)
                .totalSize(totalSize)
                .startbtn(startbtn)
                .endbtn(endbtn)
                .list(list)
                .build();

        return object;
    }
    public List<EduInfoDto> getAllClass(){
        return eduDao.getAllClass();
    }
// ====================================== 강의명 중복검사 ====================================== //
    public boolean checkClassName(String classname){
        System.out.println("EduService.checkClassName");
        return eduDao.checkClassName(classname);
    }
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

// ====================================== 전체회원 출력 [ 회원 중에 교수로 등록하려고 ] ====================================== //
    public List<MemberDto> viewMember(){
        System.out.println("EduService.viewmember");
        return eduDao.viewMember();
    }

// ====================================== 전체 회원 중에서 교수로 등록 ====================================== //
    public boolean createProfessor(ProfessorDto professorDto){
        System.out.println("EduService.createProfessor");
        System.out.println("professorDto = " + professorDto);
        return eduDao.createProfessor(professorDto);
    }

// ====================================== 다 합쳐보기 ====================================== //
    public Object getAllClassInfo(int page, int pageSize){
        System.out.println("EduService.getAllClassInfo");
        int startRow = (page-1)*pageSize;

        //총게시물수
        int totalSize = eduDao.getAllClassInfopage();
        System.out.println("totalClassTimeSize = " + totalSize);

        int totalpage = totalSize%pageSize == 0 ?
                totalSize/pageSize :
                totalSize/pageSize +1;


        int btnSize = 5;
        int startbtn = (page-1)/btnSize*btnSize+1;;
        int endbtn = startbtn + btnSize - 1;
        if(endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = eduDao.getAllClassInfo(startRow,pageSize);


        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalpage)
                .totalSize(totalSize)
                .startbtn(startbtn)
                .endbtn(endbtn)
                .list(list)
                .build();

        return object;
    }

// ====================================== 교수 수정 ====================================== //
    public int doPutProfessor(ProfessorDto professorDto){
        System.out.println("EduService.doPutProfessor");
        System.out.println("professorDto = " + professorDto);
        return eduDao.doPutProfessor(professorDto);
    }

// ====================================== 교수 ALL 출력 ====================================== //
    public Object getAllProfessor(int page, int pageSize){
        int startRow = (page-1)*pageSize;

        //총게시물수
        int totalSize = eduDao.getAllProfessorSize();

        //총페이지수
        int totalpage =  totalSize%pageSize ==0 ?
                        totalSize/pageSize :
                        totalSize/pageSize+1;

        int btnSize = 5;
        int startbtn = (page-1)/btnSize*btnSize+1;;
        int endbtn = startbtn + btnSize - 1;
        if(endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = eduDao.getAllProfessor(startRow,pageSize);

        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalpage)
                .totalSize(totalSize)
                .startbtn(startbtn)
                .endbtn(endbtn)
                .list(list)
                .build();


        return object;
    }
    public List<ProfessorDto> getProfessorName(){
        System.out.println("EduController.getProfessor");
        return eduDao.getProfessorName();
    }

// ====================================== 특정 교수만 출력 ====================================== //
    public ProfessorDto getOneProfessor(int pno){
        System.out.println("EduService.getOneProfessor");
        return eduDao.getOneProfessor(pno);
    }

// ====================================== 학기 출력 ====================================== //
    @GetMapping("/getseason")
    @ResponseBody
    public List<SeasonDto> getSeason(){
        System.out.println("EduController.getSeason");
        return eduDao.getSeason();
    }

// ====================================== 최종 강의 등록 ====================================== //
    public boolean createAllInfo(Map<String, String> map){
        System.out.println("EduController.createAllInfo");
        System.out.println(map);
        return eduDao.createAllInfo(map);
    }
// ====================================== 강의시간이 null 이면 ====================================== //
    public List<ClassTimeDto> classTimeIsNull(){
        System.out.println("EduService.classTimeIsNull");
        return eduDao.classTimeIsNull();
    }
// ====================================== 수강 등록함과 동시에 강의 시간과 강의명 업데이트 ====================================== //
    public boolean inClassAndTime(int classno, int tno){
        System.out.println("EduService.inClassAndTime");
        return eduDao.inClassAndTime(classno, tno);
    }
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
}

