package team4.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UniScheduleDto {

    private int scno;           //식별번호
    private String stdate;      //시작날짜
    private String sccontent;   //내용
    private String scolor;      //배경색

}
