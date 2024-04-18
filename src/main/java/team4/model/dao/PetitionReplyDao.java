package team4.model.dao;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PetitionReplyDao extends Dao{

    public boolean postReplyWrite(Map<String, String> map){
        try{
            String sql = "insert into preply (replycontent,mno,ppno) values(?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,map.get("pcontent"));
            ps.setString(2,map.get("mno"));
            ps.setString(3,map.get("ppno"));
            int count = ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, Object>> getReplyDo(int ppno){
        System.out.println("PetitionReplyDao.getReplyDo");
        List<Map<String, Object>> list = new ArrayList<>();
        try{
            String sql ="select * from preply where pposition = 0 and ppno = "+ppno;
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                Map<String,Object>map = new HashMap<>();
                map.put("replyno", rs.getString("replyno"));
                map.put("replycontent",rs.getString("replycontent"));
                map.put("mno",rs.getString("mno"));

                String subsql = "select * from preply where pposition=? and ppno = "+ppno;
                ps = conn.prepareStatement(subsql);
                ps.setInt(1, Integer.parseInt(rs.getString("replyno")));
                ResultSet rs1 = ps.executeQuery();
                List<Map<String , Object>> subList = new ArrayList<>();
                while(rs1.next()){
                    Map<String,Object>subMap = new HashMap<>();
                    subMap.put("replyno", rs1.getString("replyno"));
                    subMap.put("replycontent",rs1.getString("replycontent"));
                    subMap.put("mno",rs1.getString("mno"));
                    subList.add(subMap);
                }
                map.put("subReply",subList);
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
