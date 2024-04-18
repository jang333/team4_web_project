package team4.model.dao;

import org.springframework.stereotype.Component;
import team4.model.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EduDao extends Dao {
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
    // 강의실 등록
    public int roomSignUp(EduDto eduDto){
        try {
            String sql = "insert into classroom(roomnumber,totalperson)values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eduDto.getRoomnumber());
            ps.setInt(2, eduDto.getTotalperson());
            int count = ps.executeUpdate();
            if(count == 1){return 1;}
        }catch (Exception e){
            System.out.println("roomWrite SQL 오류 = " + e);
        }
        return 2;
    } // roomWrite end

    // 강의실 수정
    public int roomUpdate(EduDto eduDto, int rno){
        try {
            String sql = "update classroom set roomnumber = ?, totalperson = ? where rno = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eduDto.getRoomnumber());
            ps.setInt(2, eduDto.getTotalperson());
            ps.setInt(3, eduDto.getRno());
            int count = ps.executeUpdate();
            if(count == 1){return 1;}
        }catch (Exception e){
            System.out.println("roomUpdate SQL 오류 = " + e);
        }
        return 2;
    } // roomUpdate end

    // 강의실 출력
    public List<Object> roomInfo(int startRow, int pageSize){
        List<Object> list = new ArrayList<>();
        EduDto eduDto = null;
        try {
            String sql = "select * from classroom order by rno desc limit ?,? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()){
                eduDto = new EduDto(
                        rs.getInt("rno"),
                        rs.getInt("roomnumber"),
                        rs.getInt("totalperson")
                        );
                list.add(eduDto);
            }
        }catch (Exception e) {
            System.out.println("roomInfo SLQ 오류 = " + e);
        }
        System.out.println(list.toString());
        return list;
    } // roomInfo end

    public List<EduDto> roomInfoList(){
        System.out.println("EduController.roomInfo");
        List<EduDto> list = new ArrayList<>();
        EduDto eduDto = null;
        try {
            String sql = "select * from classroom";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                eduDto = new EduDto(
                        rs.getInt("rno"),
                        rs.getInt("roomnumber"),
                        rs.getInt("totalperson")
                );
                list.add(eduDto);
            }
        }catch (Exception e) {
            System.out.println("roomInfo SLQ 오류 = " + e);
        }
        System.out.println(list.toString());
        return list;
    }

    //강의실 전체 수
    public int roomInfototal(){
        try {
            String sql = "select count(*) from classroom";
            ps=conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 강의실 유효성, 중복체크
    public boolean roomNumCheck(int roomnumber){

        try {
            String sql = "select * from classroom where roomnumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, roomnumber);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println("checkClassRoom SQL 오류 : = " + e);
        }
        return false;
    } // checkClassRoom end

// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 송아 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //


// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
    // ======================================================================================= //
    //강의 시간 ===============================

    //강의시간 등록
    public int putEduTime(ClassTimeDto classTimeDto){
        System.out.println("EduDao.putEduTime");
        System.out.println("classTimeDto = " + classTimeDto);
        try {
            String sql = "insert into classtime (dayweek,starttime,endtime) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,classTimeDto.getDayweek());
            ps.setString(2,classTimeDto.getStarttime());
            ps.setString(3,classTimeDto.getEndtime());

            int count = ps.executeUpdate();

            if(count == 1){
                return 1;
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    //강의시간 수정
    public int updateEduTime(ClassTimeDto classTimeDto, int tno){
        System.out.println("EduDao.updateEduTime");
        try {
            String sql = "update classtime set dayweek=?, starttime = ?,endtime = ? where tno = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, classTimeDto.getDayweek());
            ps.setString(2,classTimeDto.getStarttime());
            ps.setString(3,classTimeDto.getEndtime());
            ps.setInt(4,tno);

            int count = ps.executeUpdate();

            if(count == 1){
                return 1;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    //강의시간 중복 확인 요청
    public boolean checkEduTime(ClassTimeDto classTimeDto){
        System.out.println("EduDao.checkEduTime");
        try {
            String sql = "select * from classtime where dayweek=? and starttime = ? and endtime = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, classTimeDto.getDayweek());
            ps.setString(2,classTimeDto.getStarttime());
            ps.setString(3, classTimeDto.getEndtime());
            rs=ps.executeQuery();

            if(rs.next()){
                return true; //중복됨
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false; //중복 안됨
    }

    //강의시간 출력
    public List<Object> classTime(int startRow, int pageSize){
        List<Object> list = new ArrayList<>();
        ClassTimeDto dto = null;
        try {
            String sql = "select * from classtime";
            sql+=" order by tno desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()){
                dto = new ClassTimeDto(rs.getInt("tno"),rs.getString("dayweek"),rs.getString("starttime"),rs.getString("endtime"));

                list.add(dto);
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        System.out.println("list = " + list);
        return list;
    }

    //전체 게시물수
    public int getTotalPage(){
        try {
            String sql = "select count(*) from classtime";
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



// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 혜란 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //


// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 start ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //
// ====================================== 강의 등록 [ 1:등록성공, 0:등록실패 ] ====================================== //
    public int doPostClass(String classname, String classtype){
        try {
            String sql = "insert into class(classname, classtype) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,classname);
            ps.setString(2,classtype);
            int count = ps.executeUpdate();
            System.out.println(sql);
            if(count==1){
                return 1;
            }
        }catch (Exception e){
            System.out.println("doPostClass SQL 오류 : " + e);

        }
        return 0;
    }

// ====================================== 강의 수정 [ 1:수정성공, 0:수정실패 ] ====================================== //
    public int doPutClass(String classname, String classtype, int classno){
        System.out.println("EduDao.doPutClass");
        System.out.println("classname = " + classname + ", classtype = " + classtype + ", classno = " + classno);
        try {
            String sql = "update class set classname = ?, classtype = ? where classno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,classname);
            ps.setString(2,classtype);
            ps.setInt(3,classno);
            int count = ps.executeUpdate();
            if(count==1){
                return 1;
            }
        }catch (Exception e){
            System.out.println("doPutClass SQL 오류 : " + e);
        }
        return 0;
    }
// ====================================== 강의 출력 [ , 0:등록실패 ] ====================================== //
    public List<Object> getClassInfo(int startRow, int pageSize){
        System.out.println("EduDao.getClassInfo");
        List<Object> list = new ArrayList<>();
        EduInfoDto eduInfoDto = null;
        try {
            String sql = "select * from class";

            sql+= " order by classno desc limit ?,? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                eduInfoDto = EduInfoDto.builder()
                    .classno(rs.getInt("classno"))
                    .classname(rs.getString("classname"))
                    .classtype(rs.getString("classtype"))
                    .build();
                list.add(eduInfoDto);
            }
        }catch (Exception e){
            System.out.println("getClassInfo SQL 오류 : " + e);
        }
        //System.out.println("list = " + list);
        return list;
    }
    public List<EduInfoDto> getAllClass(){
        List<EduInfoDto> list = new ArrayList<>();
        EduInfoDto eduInfoDto = null;
        try {
            String sql = "select * from class";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                eduInfoDto = EduInfoDto.builder()
                        .classno(rs.getInt("classno"))
                        .classname(rs.getString("classname"))
                        .classtype(rs.getString("classtype"))
                        .build();
                list.add(eduInfoDto);
            }
        }catch (Exception e){
            System.out.println("getClassInfo SQL 오류 : " + e);
        }
        return list;
    }

//======================================= 전체 등록된 강의 수 출력 ======================
    public int getClassTotalpage(){
        try {
            String sql = "select count(*) from class";
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
// ====================================== 강의명 중복검사 [ true:중복있음, false:중복없음 ] ====================================== //
    public boolean checkClassName(String classname){
        System.out.println("EduDao.checkClassName");
        System.out.println("classname = " + classname);
        try {
            String sql = "select * from class where classname = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,classname);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println("checkClassName SQL 오류 : " + e);        }
        return false;
    }

// ====================================== 전체회원 출력 [ 회원 중에 교수로 등록하려고 ] ====================================== //
    public List<MemberDto> viewMember(){
        System.out.println("EduDao.viewMember");
        List<MemberDto> list = new ArrayList<>();
        MemberDto memberDto = null;
        try {
            String sql = "select mno, name, birth from member";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                memberDto = MemberDto.builder()
                        .mno(rs.getInt("mno"))
                        .name(rs.getString("name"))
                        .birth(rs.getString("birth"))
                        .build();
                list.add(memberDto);
            }
        }catch (Exception e){
            System.out.println("viewMember SQL 오류 : " + e);
        }
        return list;
    }
// ====================================== 전체 회원 중에서 교수로 등록 ====================================== //
    public boolean createProfessor(ProfessorDto professorDto){
        System.out.println("EduDao.createProfessor");
        System.out.println("professorDto = " + professorDto);
        try {
            String sql = "insert into professor(pgrade, psalary, proom, degree, majorpart, mainmajor, mno_fk) values(?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, professorDto.getPgrade());
            ps.setInt(2,professorDto.getPsalary());
            ps.setString(3,professorDto.getProom());
            ps.setString(4,professorDto.getDegree());
            ps.setString(5,professorDto.getMajorpart());
            ps.setString(6,professorDto.getMainmajor());
            ps.setInt(7,professorDto.getMno());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("createProfessor SQL 오류 : " + e);
        }
        return false;
    }

// ====================================== 다 합쳐보기 ====================================== //
    public List<Object> getAllClassInfo(int startRow, int pageSize){
        System.out.println("EduDao.getAllClassInfo");
        List<Object> list = new ArrayList<>();
        EduInfoDto eduInfoDto = null;
        try {
            String sql = " select i.no, c.classname, m.name, r.roomnumber, t.dayweek, t.starttime, t.endtime, s.semester, i.cstate from classinfo i " +
                    " inner join class c on i.classno = c.classno " +
                    " inner join professor p on i.professor = p.pno " +
                    " inner join classroom r on i.roomnumber = r.rno " +
                    " inner join classtime t on i.tno = t.tno " +
                    " inner join season s on i.sno = s.sno " +
                    " inner join member m on p.mno_fk = m.mno order by no desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()){
                eduInfoDto = EduInfoDto.builder()
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
            System.out.println("getAllClassInfo SQL 오류 : " + e);
        }
        System.out.println("총 list = " + list);
        return list;
    }

    //합쳐보기 전체 페이지
    public int getAllClassInfopage(){
        System.out.println("EduDao.etAllClassInfopage");
        try{
            String sql = "select count(*) from classinfo;";
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

// ====================================== 교수 수정 [ 1:수정성공, 0:수정실패 ] ====================================== //
    public int doPutProfessor(ProfessorDto professorDto){
        System.out.println("EduDao.doPutProfessor");
        System.out.println("professorDto = " + professorDto);
        try {
            String sql = "update professor set pgrade = ?, psalary = ?, proom = ?, degree = ?, majorpart = ?, mainmajor = ? where mno_fk = ? and pno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1,professorDto.getPgrade());
            ps.setInt(2,professorDto.getPsalary());
            ps.setString(3,professorDto.getProom());
            ps.setString(4,professorDto.getDegree());
            ps.setString(5,professorDto.getMajorpart());
            ps.setString(6,professorDto.getMainmajor());
            ps.setInt(7,professorDto.getMno());
            ps.setInt(8,professorDto.getPno());
            int count = ps.executeUpdate();
            if(count == 1){
                return 1;
            }
        }catch (Exception e){
            System.out.println("doPutProfessor SQL 오류 : " + e);
        }
        return 0;
    }

// ====================================== 교수 ALL 출력 ====================================== //
    public List<Object> getAllProfessor(int startRow, int pageSize){
        System.out.println("EduDao.getProfessor");
        List<Object> list = new ArrayList<>();
        ProfessorDto professorDto = null;
        try {
            String sql = "select m.name, p.* from professor p inner join member m on p.mno_fk = m.mno order by pno desc limit ?,?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1,startRow);
            ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while (rs.next()){
                professorDto = new ProfessorDto(
                    rs.getInt("pno"),
                    rs.getString("pgrade"),
                    rs.getInt("psalary"),
                    rs.getString("proom"),
                    rs.getString("degree"),
                    rs.getString("majorpart"),
                    rs.getString("mainmajor"),
                    rs.getInt("mno_fk"),
                    rs.getString("name")
                );
                list.add(professorDto);
            }
        }catch (Exception e){
            System.out.println("getProfessor SQL 오류 : " + e);
        }
        System.out.println("list 교슈 = " + list);
        return list;
    }

    public List<ProfessorDto> getProfessorName(){
        System.out.println("EduController.getProfessor");
        List<ProfessorDto> list = new ArrayList<>();
        ProfessorDto professorDto = null;
        try {
            String sql = "select m.name, p.* from professor p inner join member m on p.mno_fk = m.mno";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                professorDto = new ProfessorDto(
                        rs.getInt("pno"),
                        rs.getString("pgrade"),
                        rs.getInt("psalary"),
                        rs.getString("proom"),
                        rs.getString("degree"),
                        rs.getString("majorpart"),
                        rs.getString("mainmajor"),
                        rs.getInt("mno_fk"),
                        rs.getString("name")
                );
                list.add(professorDto);
            }
        }catch (Exception e){
            System.out.println("getProfessor SQL 오류 : " + e);
        }
        return list;
    }
// ====================================== 교수 전체 수 ====================================== //
    public int getAllProfessorSize(){
        try {
            String sql = "select count(*) from professor";
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

// ====================================== 특정 교수만 출력 ====================================== //
    public ProfessorDto getOneProfessor(int pno){
        System.out.println("EduDao.getOneProfessor");
        ProfessorDto professorDto = null;
        try {
            String sql = "select * from professor where pno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,pno);
            rs = ps.executeQuery();
            if(rs.next()){
                professorDto = ProfessorDto.builder()
                    .pno(rs.getInt("pno"))
                    .pgrade(rs.getString("pgrade"))
                    .psalary(rs.getInt("psalary"))
                    .proom(rs.getString("proom"))
                    .degree(rs.getString("degree"))
                    .majorpart(rs.getString("majorpart"))
                    .mainmajor(rs.getString("mainmajor"))
                    .build();
                return professorDto;
            }
        }catch (Exception e){
            System.out.println("getOneProfessor SQL 오류 : " + e);
        }
        return null;
    }

// ====================================== 학기 출력 ====================================== //
    public List<SeasonDto> getSeason(){
        System.out.println("EduDao.getSeason");
        List<SeasonDto> list = new ArrayList<>();
        SeasonDto seasonDto = null;
        try {
            String sql = "select * from season";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                seasonDto = SeasonDto.builder()
                        .sno(rs.getInt("sno"))
                        .semester(rs.getString("semester"))
                        .build();
                list.add(seasonDto);
            }
        }catch (Exception e){
            System.out.println("getSeason SQL 오류 : " + e);
        }
        return list;
    }

// ====================================== 최종 강의 등록 ====================================== //
    public boolean createAllInfo(Map<String, String> map){
        System.out.println("EduController.createAllInfo");
        System.out.println("넌뭐냐 = " + map);
        try {
            String sql = "insert into classinfo(classno, professor, roomnumber, tno, sno) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,map.get("classno"));
            ps.setString(2,map.get("professor"));
            ps.setString(3,map.get("roomnumber"));
            ps.setString(4,map.get("tno"));
            ps.setString(5,map.get("sno"));
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("createAllInfo SQL 오류 : " + e);
        }
        return false;
    }

// ====================================== 강의시간이 null 이면 ====================================== //
    public List<ClassTimeDto> classTimeIsNull(){
        System.out.println("EduDao.classTimeIsNull");
        List<ClassTimeDto> list = new ArrayList<>();
        ClassTimeDto classTimeDto = null;
        try {
            String sql = "select * from classtime where classno is null";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                classTimeDto = new ClassTimeDto(
                        rs.getInt("tno"),
                        rs.getString("dayweek"),
                        rs.getString("starttime"),
                        rs.getString("endtime")
                );
                list.add(classTimeDto);
            }
        }catch (Exception e){
            System.out.println("classTimeIsNull SQL 오류 : " + e);
        }
        return list;
    }

    // ====================================== 강의랑 강의시간 매칭 ====================================== //
    public boolean inClassAndTime(int classno, int tno){
        System.out.println("EduDao.inClassAndTime");
        System.out.println("classno = " + classno + ", tno = " + tno);
        try {
            String sql = "update classtime set classno=? where tno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,classno);
            ps.setInt(2,tno);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("updateClassAndClassTime SQL 오류 : " + e);
        }
        return false;
    }


}
// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 효성 작업 end ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ //

