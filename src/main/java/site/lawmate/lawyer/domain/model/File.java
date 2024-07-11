package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "id")
@Document(collection = "files")
public class File {
    @Id
    String id;
    String filename;
    String contentType;
    byte[] data;
    String lawyerId;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime modifiedDate;
}
