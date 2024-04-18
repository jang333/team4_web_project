package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.MemberDao;
import team4.model.dto.LoginDto;
import team4.model.dto.MemberDto;

@Service
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    public boolean doPostSignup(MemberDto memberDto){
        boolean result = memberDao.doPostSignup(memberDto);

        return result;
    }
    public MemberDto doGetLoginInfo(String id){
        return memberDao.doGetLoginInfo(id);
    }

    public boolean doGetFindIdCheck(String id){
        return memberDao.doGetFindIdCheck(id);
    }

}
