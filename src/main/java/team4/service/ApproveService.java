package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.ApproveDao;
import team4.model.dto.EduInfoDto;

import java.util.List;

@Service
public class ApproveService {
    @Autowired
    private ApproveDao approveDao;
    public List<EduInfoDto> putApprove(){
        System.out.println("ApproveService.putApprove");
        return approveDao.putApprove();
    }

    public boolean putchangeStateClass(String cstate, String cno){

        return approveDao.putchangeStateClass(cstate, cno);
    }
}
