package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResDto {
    private String id;
    private String date;
    private String startTime;
    private String endTime;
    private String status;
    private String userId;
    private String lawyerId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
