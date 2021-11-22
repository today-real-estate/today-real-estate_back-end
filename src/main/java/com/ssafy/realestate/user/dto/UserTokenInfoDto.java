package com.ssafy.realestate.user.dto;

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

}
