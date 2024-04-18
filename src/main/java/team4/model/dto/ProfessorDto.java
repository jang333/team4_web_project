package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProfessorDto {
    private int pno;			            //  교수 번호
    private String pgrade;				    // 직원 등급
    private int psalary;					// 교수 급여
    private String proom;					// 교수 강의실 위치
    private String degree;					// 교수 학위
    private String majorpart;				// 교수 전공
    private String mainmajor;				// 교수 담당 학과
    private int mno;						// member 테이블 mno

    private String name;
}
