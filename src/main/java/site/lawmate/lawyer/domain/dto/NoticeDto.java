package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NoticeDto {
    private String id;
    private String message;
    private String userId;
    private String response;
    private String lawyerId;
    private String attachment;
    private String status;
}
