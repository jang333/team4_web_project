package team4.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SeasonDto {
    private int sno;
    private String semester;
    private String startDate;
    private String endDate;
}
