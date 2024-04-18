package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.EduDto;
import team4.model.dto.EnrolmentDto;
import team4.model.dto.EnrolmentlistDto;

import java.util.ArrayList;
import java.util.List;

@Component
public  class EnrolmentDao extends Dao {

    public int searchMno(String loginDto){
        System.out.println("EnrolmentDao.searchMno");
        System.out.println("loginDto = " + loginDto);
        try {
            String sql = "select mno from member where id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,loginDto);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("rs.getInt(\"mno\") = " + rs.getInt("mno"));
                return rs.getInt("mno");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public int postRegist(EnrolmentDto enrolmentDto){
        System.out.println(enrolmentDto.getClassno());
        System.out.println("enrolmentDto = " + enrolmentDto.getMno());
        
        try {
            String sql = "insert into enrolment (classno, mno) value (?,?) ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,enrolmentDto.getClassno());
            ps.setInt(2,enrolmentDto.getMno());
            int count = ps.executeUpdate();
            if(count==1){
                return 1;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    public List<Object> getClassList(int mno, int page, int pageSize){
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from enrolment e inner join class c on e.classno = c.classno where mno = ? ";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,mno);
            rs=ps.executeQuery();
            while(rs.next()){
                EnrolmentlistDto enrolmentlistDto = new EnrolmentlistDto(rs.getInt("classno"),
                        rs.getString("classname"),
                        rs.getString("classtype"),
                        rs.getInt("mno")
                );
                list.add(enrolmentlistDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("list는???????????" + list);
        return list;
    }

    //수강신청내역 전체 수
    public int getClassListTotal(){
        try {
            String sql = "select count(*) from enrolment";
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

}
