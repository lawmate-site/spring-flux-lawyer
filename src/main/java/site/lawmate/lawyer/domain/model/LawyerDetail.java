package site.lawmate.lawyer.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "lawyer_details")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor
public class LawyerDetail implements Persistable<String> {
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
    String time;
    Boolean premium;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime modifiedDate;

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
