package SocialMedia.Models;

import SocialMedia.Enums.TypeBanAccountEnum;
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
public class BanAccountModel {
    private TypeBanAccountEnum viewType;
    private String avatar;
    private String username;
    private String fullname;
    private boolean isBanned;
}
