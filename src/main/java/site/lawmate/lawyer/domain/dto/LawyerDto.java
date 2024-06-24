package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import site.lawmate.lawyer.domain.model.LawyerDetailModel;
import site.lawmate.lawyer.domain.model.PostModel;
import site.lawmate.lawyer.domain.model.ReplyModel;

import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LawyerDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String birth;
    private String lawyerNo;
    private String account;
    private Boolean auth;

    private List<PostModel> posts;
    private List<ReplyModel> replies;
    private LawyerDetailModel detail;
}
