package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.EduInfoDto;
import team4.model.dto.ProfessorDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorDao extends Dao{

    public List<EduInfoDto> doGetProfessorClass(int mno){
        System.out.println("ProfessorDao.doGetProfessorClass");
        System.out.println("mno = " + mno);
        List<EduInfoDto> list = new ArrayList<>();
        try{
            String sql = "select i.no, c.classname, m.name, r.roomnumber, t.dayweek, t.starttime, t.endtime, s.semester, i.cstate from classinfo i " +
                    "   inner join class c on i.classno = c.classno " +
                    "   inner join professor p on i.professor = p.pno " +
                    "    inner join classroom r on i.roomnumber = r.rno " +
                    "   inner join classtime t on i.tno = t.tno " +
                    "   inner join season s on i.sno = s.sno " +
                    "    inner join member m on p.mno_fk = m.mno where mno_fk = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,mno);
            rs= ps.executeQuery();
            while (rs.next()){
                EduInfoDto eduInfoDto = EduInfoDto.builder()
                        .no(rs.getInt("no"))
                        .classname(rs.getString("classname"))
                        .professorname(rs.getString("name"))
                        .roomnumber(rs.getInt("roomnumber"))
                        .dayweek(rs.getString("dayweek"))
                        .starttime(rs.getString("starttime"))
                        .endtime(rs.getString("endtime"))
                        .semester(rs.getString("semester"))
                        .cstate(rs.getInt("cstate"))
                        .build();
                list.add(eduInfoDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(list.toString());
        return list;
    }
}
