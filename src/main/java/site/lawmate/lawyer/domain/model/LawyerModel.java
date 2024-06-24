package site.lawmate.lawyer.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "lawyers")
@Builder
@AllArgsConstructor
@ToString(exclude = "id")
@NoArgsConstructor

public class LawyerModel {
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
    List<PostModel> posts;
    List<ReplyModel> replies;
    List<ResModel> reservations;
    List<NoticeModel> notices;
    LawyerDetailModel detail;


}
