package team4.model.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@ToString@Builder
public class EnrolmentlistDto {
    private int classno;
    private String classname;
    private String classtype;
    private int mno;


}
