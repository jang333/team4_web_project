package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.model.dao.ProfessorDao;
import team4.model.dto.EduInfoDto;
import team4.model.dto.ProfessorDto;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorDao professorDao;

    public List<EduInfoDto> doGetProfessorClass(int mno){
        System.out.println("ProfessorService.doGetProfessorClass");
        System.out.println("mno = " + mno);
        return professorDao.doGetProfessorClass(mno);
    }

}
