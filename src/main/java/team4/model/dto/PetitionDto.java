package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PetitionDto {
    private int ppno;             // 게시물 번호
    private String ptitle;          // 글 제목
    private String pcontent;        // 글 내용
    private int participation;      // 청원 참여 회수
    private String regidate;           // 등록날짜
    private String duedate;            // 마감날짜
    private int pstate;             // 청원 상태 0:접수 1:진행 2:마감
    private int mno;             // 청원 상태 0:접수 1:진행 2:마감
    private String name;


    String id;


    public PetitionDto(int ppno, String ptitle, int participation, String regidate, String duedate) {
        this.ppno = ppno;
        this.ptitle = ptitle;
        this.participation = participation;
        this.regidate = regidate;
        this.duedate = duedate;
    }

    public PetitionDto(int ppno, String ptitle, String pcontent, int participation, String duedate, int pstate, String name) {
        this.ppno = ppno;
        this.ptitle = ptitle;
        this.pcontent = pcontent;
        this.participation = participation;
        this.duedate = duedate;
        this.pstate = pstate;
        this.name = name;
    }

}
