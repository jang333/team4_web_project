package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder // 생성자 단점을 보완한 라이브러리 함수 제공
public class EduInfoDto {
    private int classno;
    private String classname;
    private String classtype;
    private int cstate;
// ============== GET ALL CLASS INFO ============== //
    private int no;
    private int tno;
    private String professorname;
    private int roomnumber;
    private String dayweek;
    private String starttime;
    private String endtime;
    private String semester;

}
