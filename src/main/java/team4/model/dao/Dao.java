package team4.model.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Component
public class Dao {
    public Connection conn;        //DB연동 인터페이스
    public PreparedStatement ps;   //sql실행, 매개변수 인터페이스 제공
    public ResultSet rs;           //sql 실행 결과를 호출하는 인터페이스 제공
    //2.생성자 : DB연동 코드
    public Dao() {
        try {
            //1.jdbc라이브러리 호출
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/upteam4","root","1234");

            System.out.println("DB success ");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
