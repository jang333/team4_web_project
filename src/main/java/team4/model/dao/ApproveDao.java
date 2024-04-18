package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.EduInfoDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApproveDao extends Dao{

    public List<EduInfoDto> putApprove(){
        List<EduInfoDto> list = new ArrayList<>();
        try{
            String sql ="select i.no, c.classname, i.professor, m.name, r.roomnumber, t.dayweek, t.starttime, t.endtime, s.semester, i.cstate from classinfo i " +
                    "   inner join class c on i.classno = c.classno           " +
                    "   inner join professor p on i.professor = p.pno         " +
                    "    inner join classroom r on i.roomnumber = r.rno       " +
                    "   inner join classtime t on i.tno = t.tno               " +
                    "   inner join season s on i.sno = s.sno                  " +
                    "    inner join member m on p.mno_fk = m.mno order by no;";
            ps =conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                EduInfoDto eduInfoDto = EduInfoDto.builder()
                        .no(rs.getInt("no"))
                        .classname(rs.getString("classname"))
                        .professorname(rs.getString("professor"))
                        .roomnumber(rs.getInt("roomnumber"))
                        .dayweek(rs.getString("dayweek"))
                        .starttime(rs.getString("starttime"))
                        .endtime(rs.getString("endtime"))
                        .semester(rs.getString("semester"))
                        .cstate(rs.getInt("cstate"))
                        .build();
                list.add(eduInfoDto);
            }
            System.out.println("list = " + list);
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public boolean putchangeStateClass(String cstate , String cno){
        System.out.println("ApproveDao.putchangeStateClass");
        try{
            String sql = "update classinfo set cstate = ? where no = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,cstate);
            ps.setString(2,cno);
            int count = ps.executeUpdate();
            if(count ==1 ){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
