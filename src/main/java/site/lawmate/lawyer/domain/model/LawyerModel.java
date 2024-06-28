package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "lawyers")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor

public class LawyerModel implements Serializable {
    @Id
    String id;
    String username;
    String email;
    String password;
    String name;
    String phone;
    String birth;
    String lawyerNo;
    String mid;
    Boolean auth;

    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime modifiedDate;

//    List<PostModel> posts;
//    List<FileModel> files;
//    List<ReplyModel> replies;
//    List<ResModel> reservations;
//    List<NoticeModel> notices;
    LawyerDetailModel detail;

}
