package SocialMedia.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageModel {
    private String viewType;
    private String username;
    private String fullname;
    private String messageSendingAt;
    private String text;
    private String media;
    private Boolean seen;

}