package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResDto {
    private String id;
    private String lawyerId;
    private String userId;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
}
