package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(collection = "lawyers")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor

public class Lawyer implements Serializable {
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
    LawyerDetail detail;

}
