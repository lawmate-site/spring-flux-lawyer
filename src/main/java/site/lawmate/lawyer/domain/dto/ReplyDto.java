package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReplyDto {
    private Long id;
    private String content;
    private String articleId;
    private String lawyerId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
