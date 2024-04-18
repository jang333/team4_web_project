package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
@Builder
public class MemberDto {
  private int mno;
  private String id;
  private String pw;
  private String name;
  private String phone;
  private String email;
  private String address;
  private String birth;
  private int state;
}
