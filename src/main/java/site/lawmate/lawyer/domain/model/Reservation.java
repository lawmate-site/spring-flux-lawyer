package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reservations")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class Reservation {
    @Id
    String id;
    String date;
    String startTime;
    String endTime;
    String status;
    String userId;
    String lawyerId;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime modifiedDate;

}
