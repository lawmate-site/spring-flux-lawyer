package site.lawmate.lawyer.domain.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("tokens")
@ToString
public class Token {
    @Id
    private String id;
    private String refreshToken;
    private String email;
    private Date expiration;

}
