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
public class PostDto {
    private String id;
    private String title;
    private String content;
    private String category;
    private String lawyerId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
