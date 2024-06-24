package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reservations")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class ResModel {
    @Id
    String id;
    String lawyerId;
    String userId;
    String date;
    String startTime;
    String endTime;
    String status;
}
