package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
@Builder
public class EnrolmentDto {
    private int classno;
    private int mno;
}
