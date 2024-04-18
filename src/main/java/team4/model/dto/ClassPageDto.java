package team4.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class ClassPageDto {
    private int page;       //현재 페이지
    private int totalpage;  //전체 페이지
    private int startbtn;   //시작 번호
    private int endbtn;     //마지막 번호
    private int totalSize; //전체 게시물 수

    //실제 내용
    private List<Object> list;

}
