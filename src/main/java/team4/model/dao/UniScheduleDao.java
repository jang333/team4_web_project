package team4.model.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team4.model.dto.UniScheduleDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniScheduleDao extends Dao {

    //학사 일정 등록
    public boolean doSchedulWrite(UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleDao.doSchedulWrite");
        try {
            String sql = "insert into unischedule (stdate,sccontent, scolor) value (?, ?, ?) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, uniScheduleDto.getStdate());
            ps.setString(2, uniScheduleDto.getSccontent());
            ps.setString(3, uniScheduleDto.getScolor());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("스케줄등록 e = " + e);
        }

        return false;
    }

    //학사 일정 출력 (관리자)
    public List<Object> getSchedul(int startRow, int pageSize){
        System.out.println("UniScheduleDao.getSchedul");
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from unischedule order by scno desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()){
                UniScheduleDto data = UniScheduleDto.builder()
                        .scno(rs.getInt("scno"))
                        .stdate(rs.getString("stdate"))
                        .sccontent(rs.getString("sccontent"))
                        .scolor(rs.getString("scolor"))
                        .build();
                list.add(data);
            }

        }catch (Exception e){
            System.out.println("스케쥴 출력 e = " + e);
        }
        System.out.println(list);
        return list;
    }

    //학사 일정 출력 (달력)
    public List<Object> getSchedulCalendar(){
        System.out.println("UniScheduleDao.getSchedul");
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from unischedule";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                UniScheduleDto data = UniScheduleDto.builder()
                        .scno(rs.getInt("scno"))
                        .stdate(rs.getString("stdate"))
                        .sccontent(rs.getString("sccontent"))
                        .scolor(rs.getString("scolor"))
                        .build();
                list.add(data);
            }

        }catch (Exception e){
            System.out.println("스케쥴 출력 e = " + e);
        }
        System.out.println(list);
        return list;
    }


    //학사일정 수
    public int getSchedulTotal(){
        try {
            String sql = "select count(*) from unischedule";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    //학사 일정 수정 전 정보
    public List<UniScheduleDto> getSchedulUpdateInfo(int scno){
        System.out.println("학사일정수정정보scno = " + scno);
        System.out.println("UniScheduleDao.getSchedulUpdateInfo");
        List<UniScheduleDto> list =new ArrayList<>();
        try{
            String sql = "select * from unischedule where scno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,scno);
            rs = ps.executeQuery();
            if(rs.next()){
                UniScheduleDto data = UniScheduleDto.builder()
                        .scno(scno)
                        .stdate(rs.getString("stdate"))
                        .scolor(rs.getString("scolor"))
                        .sccontent(rs.getString("sccontent"))
                        .build();
                list.add(data);
            }
        }catch (Exception e){
            System.out.println("일정 수정 전 정보 e = " + e);
        }
        System.out.println(list);
        return list;
    }

    //학사 일정 수정
    public boolean doSchedulUpdate(int scno, UniScheduleDto uniScheduleDto){
        System.out.println("UniScheduleDao.doSchedulUpdate");
        System.out.println("scno = " + scno);
        try{
            String sql = "update unischedule set stdate = ?,sccontent = ?, scolor = ? where scno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,uniScheduleDto.getStdate());
            ps.setString(2, uniScheduleDto.getSccontent());
            ps.setString(3, uniScheduleDto.getScolor());
            ps.setInt(4,uniScheduleDto.getScno());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("스케쥴 수정e = " + e);
        }
        return false;
    }

    //학사 일정 삭제
    public boolean doSchedulDelete(int scno){
        System.out.println("UniScheduleDao.doSchedulDelete");
        try {
            String sql = "delete from unischedule where scno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,scno);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("학사일정삭제 e = " + e);
        }
        return false;
    }
}
