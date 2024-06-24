package site.lawmate.lawyer.domain.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LawyerDetailDto {
    private String id;
    private String belong;
    private String address;
    private String addressDetail;
    private String belongPhone;
    private String image;
    private String law;
    private String visitCost;
    private String phoneCost;
    private String videoCost;
    private String university;
    private String major;
    private Boolean premium;
}