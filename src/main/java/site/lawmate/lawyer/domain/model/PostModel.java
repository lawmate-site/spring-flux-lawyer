package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "posts")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class PostModel {
    @Id
    String id;
    String title;
    String content;
    String lawyerId;
}
