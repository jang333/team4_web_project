package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.MemberDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorizationDao extends Dao{

    public List<Object> doGetAuthorization(int startRow , int pageSize){
        System.out.println("AuthorizationDao.doGetAuthorization");
        List<Object> list = new ArrayList<>();
        try{
            String sql = "select * from member order by mno desc limit ?,?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs= ps.executeQuery();
            while (rs.next()){
                MemberDto memberDto = MemberDto.builder()
                        .mno(rs.getInt("mno"))
                        .name(rs.getString("name"))
                        .state(rs.getInt("state"))
                        .build();
                list.add(memberDto);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //전체 멤버 수
    public int getTotalMember(){
        try {
            String sql = "select count(*) from member";
            ps= conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    public boolean doPutchangestate(int state, MemberDto memberDto){
        System.out.println("AuthorizationDao.doPutchangestate");
        System.out.println("state = " + state + ", memberDto = " + memberDto.getMno());
        try {
            String sql = "update member set state = ? where mno=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,state);
            ps.setInt(2,memberDto.getMno());
            int count = ps.executeUpdate();
            if(count==1){
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
