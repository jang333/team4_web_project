package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.EnrolmentDao;
import team4.model.dto.ClassPageDto;
import team4.model.dto.EnrolmentDto;
import team4.model.dto.EnrolmentlistDto;

import java.util.List;

@Service
public class EnrolmentService {
    @Autowired
    EnrolmentDao enrolmentDao;
    public int searchMno(String loninDto){
        System.out.println("EnrolmentService.searchMno");
        return enrolmentDao.searchMno(loninDto);
    }
    public int postRegist( EnrolmentDto enrolmentDto){
        System.out.println("EnrolmentService.postRegist");
        System.out.println("service enrolmentDto = " + enrolmentDto);

        return enrolmentDao.postRegist(enrolmentDto);
    }

    public Object getClassList(int mno, int page, int pageSize){
        int startRow = (page-1)*pageSize;

        int totalSize = enrolmentDao.getClassListTotal();

        int totalpage = totalSize%pageSize == 0 ?
                totalSize/pageSize :
                totalSize/pageSize +1;
        System.out.println("totalpage = " + totalpage);

        int btnSize = 5;
        int startbtn = (page-1)/btnSize*btnSize+1;;
        int endbtn = startbtn + btnSize - 1;
        if(endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = enrolmentDao.getClassList(mno,startRow, pageSize);

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

}
