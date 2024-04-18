package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.PetitionDao;
import team4.model.dao.PetitionReplyDao;

import java.util.List;
import java.util.Map;

@Service
public class PetitionReplyService {
    @Autowired
    PetitionReplyDao petitionReplyDao;
    public boolean postReplyWrite(Map<String, String>map){
        return petitionReplyDao.postReplyWrite(map);
    }

    public List<Map<String, Object>> getReplyDo(int ppno){
        System.out.println("PetitionReplyController.getReplyDo");
        return petitionReplyDao.getReplyDo(ppno);

    }


}
