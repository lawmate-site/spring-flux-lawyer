package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notices")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class NoticeModel {
    @Id
    String id;
    String message; // user message
    String userId;
    String response; // lawyer response
    String lawyerId;
    String attachment;
    String status;
}
