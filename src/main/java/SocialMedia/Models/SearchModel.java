package SocialMedia.Models;

import SocialMedia.Enums.TypeSearchEnum;
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
public class SearchModel {
    private TypeSearchEnum viewType;
    private String avatar;
    private long groupdId;
    private String username;
    private String fullName;
    private String requestTimeAt;
    private long groupModeId;
    private String groupModeType;
}
