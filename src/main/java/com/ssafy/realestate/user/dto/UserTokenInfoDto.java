package com.ssafy.realestate.user.dto;

import com.ssafy.realestate.user.entity.UserAuthority;
import com.ssafy.realestate.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenInfoDto {
    private Long id;
    private String userName;
    private String userEmail;
    private String nickname;
    private String recentSearch;
    private String token;
    private Long authority;

    public static UserTokenInfoDto from(UserEntity user,String token) {
        return new UserTokenInfoDto(user.getId(), user.getUserEmail(), user.getUserName(), user.getNickname(), user.getRecentSearch(), token,
                authorityTransfer(user.getAuthorities().get(0)) );
    }

    private static Long authorityTransfer(UserAuthority userAuthority) {
       return userAuthority.getAuth().getId();
    }

}
