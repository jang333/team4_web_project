package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.AuthorizationDao;
import team4.model.dto.ClassPageDto;
import team4.model.dto.MemberDto;

import java.util.List;

@Service
public class AuthorizationService {
    @Autowired
    AuthorizationDao authorizationDao;
    public Object doGetAuthorization(int page, int pageSize){
        System.out.println("AuthorizationService.doGetAuthorization");
        int startRow = (page-1)*pageSize;

        int totalSize = authorizationDao.getTotalMember();

        int totalpage = totalSize%pageSize == 0 ?
                        totalSize/pageSize :
                        totalSize/pageSize+1;

        int btnSize = 5;
        int startbtn = (page - 1) / btnSize * btnSize + 1;
        int endbtn = startbtn + btnSize - 1;
        if (endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = authorizationDao.doGetAuthorization(startRow,pageSize);


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

    public boolean doPutchangestate(int state, MemberDto memberDto){
        System.out.println("AuthorizationService.doPutchangestate");
        return authorizationDao.doPutchangestate(state, memberDto);
    }
}
