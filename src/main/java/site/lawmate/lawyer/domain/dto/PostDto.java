package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;

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
    private String lawyerId;
}
