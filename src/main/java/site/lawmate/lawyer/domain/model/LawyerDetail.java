package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "lawyer_details")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class LawyerDetail {
    @Id
    String id;
    String belong;
    String address;
    String addressDetail;
    String belongPhone;
    String law;
    String visitCost;
    String phoneCost;
    String videoCost;
    String university;
    String major;
    Boolean premium;

    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime modifiedDate;

}
