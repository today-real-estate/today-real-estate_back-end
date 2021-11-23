package com.ssafy.realestate.user.dto;

import com.ssafy.realestate.user.entity.UserAuthority;
import com.ssafy.realestate.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String userEmail;
    private String userName;
    private String nickname;
    private String recentSearch;
    private String profileImage;
    private Long authority;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static UserResponseDto from(UserEntity user) {
        return new UserResponseDto(user.getId(), user.getUserEmail(), user.getUserName(), user.getNickname(), user.getRecentSearch(), "",
                authorityTransfer(user.getAuthorities().get(0)), user.getCreatedDate(), user.getModifiedDate());
    }

    private static Long authorityTransfer(UserAuthority userAuthority) {
        return userAuthority.getAuth().getId();
    }
}