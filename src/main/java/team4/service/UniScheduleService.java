package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dao.UniScheduleDao;
import team4.model.dto.ClassPageDto;
import team4.model.dto.UniScheduleDto;

import java.util.List;

@Service
public class UniScheduleService {

    @Autowired
    UniScheduleDao uniScheduleDao;

    //학사 일정 등록
    public boolean doSchedulWrite(UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleService.doSchedulWrite");

        return uniScheduleDao.doSchedulWrite(uniScheduleDto);
    }

    //학사 일정 출력
    public Object getSchedul(int page, int pageSize ){
        if(page != 0) {//학사 일정 출력 (관리자)
            System.out.println("UniScheduleService.getSchedul");
            int startRow = (page - 1) * pageSize;

            //총게시물수
            int totalSize = uniScheduleDao.getSchedulTotal();
            System.out.println("totalClassTimeSize = " + totalSize);

            int totalpage = totalSize % pageSize == 0 ?
                    totalSize / pageSize :
                    totalSize / pageSize + 1;
            System.out.println("totalpage = " + totalpage);

            int btnSize = 5;
            int startbtn = (page - 1) / btnSize * btnSize + 1;
            int endbtn = startbtn + btnSize - 1;
            if (endbtn >= totalpage) endbtn = totalpage;

            List<Object> list = uniScheduleDao.getSchedul(startRow, pageSize);


            Object object = ClassPageDto.builder()
                    .page(page)
                    .totalpage(totalpage)
                    .totalSize(totalSize)
                    .startbtn(startbtn)
                    .endbtn(endbtn)
                    .list(list)
                    .build();

            return object;
        }else {//학사 일정 출력 (달력)
            List<Object> list = uniScheduleDao.getSchedulCalendar();
            Object object = ClassPageDto.builder()
                    .list(list)
                    .build();

            return object;
        }

    }

    //학사 일정 수정 전 정보
    public List<UniScheduleDto> getSchedulUpdateInfo(@RequestParam int scno){
        System.out.println("UniScheduleService.getSchedulUpdateInfo");

        return uniScheduleDao.getSchedulUpdateInfo(scno);
    }

    //학사 일정 수정
    public boolean doSchedulUpdate(int scno, UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleService.doSchedulUpdate");
        return uniScheduleDao.doSchedulUpdate(scno, uniScheduleDto);
    }

    //학사 일정 삭제
    public boolean doSchedulDelete(int scno){
        System.out.println("UniScheduleService.doSchedulDelete");
        return uniScheduleDao.doSchedulDelete(scno);
    }





}
