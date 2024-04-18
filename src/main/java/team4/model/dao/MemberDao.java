package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.LoginDto;
import team4.model.dto.MemberDto;

@Component
public class MemberDao extends Dao{

    public boolean doPostSignup(MemberDto memberDto){
        try{
            String sql = "insert into member (id,pw,name,phone,email,address,birth) values(?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1, memberDto.getId());
            ps.setString(2, memberDto.getPw());
            ps.setString(3, memberDto.getName());
            ps.setString(4, memberDto.getPhone());
            ps.setString(5, memberDto.getEmail());
            ps.setString(6, memberDto.getAddress());
            ps.setString(7, memberDto.getBirth());
            int count = ps.executeUpdate();
            if(count ==1 ){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean doPostLogin(LoginDto loginDto){
        System.out.println("loginDto = " + loginDto);
        try {

            String sql = "select * from member where id=? and pw =?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, loginDto.getId());
            ps.setString(2, loginDto.getPw());
            rs=ps.executeQuery();
            if(rs.next()){

                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public MemberDto doGetLoginInfo(String id){
        MemberDto memberDto = null;
        try {
            String sql ="select * from member where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                memberDto = new MemberDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return memberDto;
    }

    public boolean doGetFindIdCheck(String id){
        try {
            String sql = "select * from member where id = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
