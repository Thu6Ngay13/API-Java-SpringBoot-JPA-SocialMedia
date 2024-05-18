package SocialMedia.Models;

import SocialMedia.Enums.TypeMessageEnum;
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
    private TypeMessageEnum viewType;
    private String username;
    private String fullname;
    
    private long messageId;
    private long conversationId;
    
    private String messageSendingAt;
    private String text;
    private String mediaURL;
    private Boolean seen;
}