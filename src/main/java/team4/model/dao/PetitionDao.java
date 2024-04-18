package team4.model.dao;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import team4.model.dto.PetitionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 청원게시글
@Component
public class PetitionDao extends Dao {

    // 청원게시글 등록
    public int PetitionWrite(PetitionDto petitionDto) {
        System.out.println("PetitionDao.PetitionWrite");
        System.out.println("petitionDto = " + petitionDto);
        try {
            String sql = "insert into petition(regidate, duedate, ptitle, pcontent, mno)values(?,?,?,?,?)";
            System.out.println("petitionDto = " + petitionDto);
            ps = conn.prepareStatement(sql);
            ps.setString(1, petitionDto.getRegidate());
            ps.setString(2, petitionDto.getDuedate());
            ps.setString(3, petitionDto.getPtitle());
            ps.setString(4, petitionDto.getPcontent());
            ps.setInt(5, petitionDto.getMno());
            int count = ps.executeUpdate();
            if (count == 1) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("PetitionWrite SQL 오류 = " + e);
        }
        return 0;
    }

    // 청원게시글 출력
    public List<Object> PetitiotionList(int startRow, int pageSize) {
        System.out.println("PetitionDao.PetitiotionList");
        List<Object> list = new ArrayList<>();
        PetitionDto petitionDto = null;
        try {
            String sql = "select * from petition order by ppno desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                petitionDto = new PetitionDto(
                        rs.getInt("ppno"),
                        rs.getString("ptitle"),
                        rs.getInt("participation"),
                        rs.getString("regidate"),
                        rs.getString("duedate")
                );
                list.add(petitionDto);
                System.out.println(petitionDto.toString());
                System.out.println(list.toString());
            }
        } catch (Exception e) {
            System.out.println("PetitiotionList SQL 오류 : " + e);
        }
        System.out.println("최종" + list);
        return list;
    }

    //청원 게시물 전체 수
    public int PetitiotionListTotal(){
        try {
            String sql = "select count(*) from petition";
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

    // 청원게시글 개별 출력
    public PetitionDto PetitionView(int ppno){
        PetitionDto petitionDto = null;
        System.out.println("PetitionDao.PetitionView");
        try{
            String sql = "select p.*, m.name from petition p inner join member m on p.mno = m.mno where p.ppno= ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ppno);
            rs = ps.executeQuery();
            if (rs.next()){
                petitionDto = new PetitionDto(
                        rs.getInt("ppno"), rs.getString("ptitle"),
                        rs.getString("pcontent"), rs.getInt("participation"),
                        rs.getString("duedate"),rs.getInt("pstate"),
                        rs.getString("name"));
            }
            System.out.println(petitionDto);
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        System.out.println(ppno);
        return petitionDto;
    }

    // 청원게시글 삭제
    public boolean petitionDelete(@RequestParam int ppno){
        System.out.println("petitionDelete ppno = " + ppno);
        System.out.println("PetitionDao.petitionDelete");
        try {
            String sql = "delete from petition where ppno =" + ppno;
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate(); if(count==1) return true;
        }catch (Exception e) {
            System.out.println("e = " + e);
        }
        return false;
    }

    //  게시물 작성자 인증
    public boolean petitionWriterAuth(int ppno, String id){
        System.out.println("PetitionDao.petitionWriterAuth");
        try {
            String sql = "select * from petition p inner join member m" +
                    " on p.mno = p.mno " +
                    "where p.ppno = ? and m.id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,ppno);
            ps.setString(2, id);
            rs= ps.executeQuery();
            if(rs.next()) {return true;}
        }catch (Exception e) {
            System.out.println("e = " + e);
        }
        return false;
    }

    // 청원게시글 동의하기
    public int petitionAgree(int ppno, int mno) {
        try {
            String sql = "select * from participation where ppno=? and mno =?";
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , ppno );    ps.setInt( 2 , mno );
            rs = ps.executeQuery();
            if(rs.next()){
                return -2;
            }
            sql = "insert into participation values( ? , ? )";
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , ppno );    ps.setInt( 2 , mno );
            int count = ps.executeUpdate();
            if (count == 1){sql = "select count(*) from participation where ppno = ?"; ps= conn.prepareStatement(sql); ps.setInt( 1 , ppno );
            rs = ps.executeQuery();
            if(rs.next()){
                rs.getInt("count(*)");
                sql = "update petition set participation = ? where ppno=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1,rs.getInt("count(*)"));
                ps.setInt(2,ppno);
                int count2 = ps.executeUpdate();
                if(count2==1){return rs.getInt("count(*)");}
                }
            };
        } catch (Exception e) {
            System.out.println("petitionAgree e = " + e);
        }
        return -1;
    }

    // 댓글 등록
    public boolean replyWrite(Map<String, String> map){
        try {
            String sql = "insert into preply (replycontent,replyno,mno,ppno)value(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("replycontent"));
            ps.setString(2, map.get("replyno"));
            ps.setString(3, map.get("mno"));
            ps.setString(4, map.get("ppno"));
            int count = ps.executeUpdate();
            if(count==1) {return true;}
        }catch (Exception e) {
            System.out.println("e = " + e);
        }
        return false;
    }

    // 댓글 출력
    public List<Map<String, Object>> getReply (int ppno){
        List<Map<String, Object>> list = new ArrayList<>();
        System.out.println("PetitionDao.getReply");
        try {
            // 상위 댓글 출력
            String sql = "select * from preply where ppno =" +ppno;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Map<String, Object> map = new HashMap<>();
                map.put("replyno", rs.getString("replyno"));
                map.put("replycontent", rs.getString("replycontent"));
                map.put("mno", rs.getString("mno"));
                list.add(map);
            }
        }catch (Exception e) {
            System.out.println("e = " + e);
        }
        return list;
    }
} // dao end