package com.ssafy.realestate.user.dto;

import com.ssafy.realestate.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRecentSearchDto {
    @Id
    private Long id;

    @NotBlank(message = "Dong 코드가 필요합니다.")
    private String dongCode;

    public UserEntity toUserRecentSearchEntity() {
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .recentSearch(dongCode)
                .build();
        return userEntity;
    }
}
