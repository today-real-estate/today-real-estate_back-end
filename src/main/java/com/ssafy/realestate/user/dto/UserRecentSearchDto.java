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
    private String dongName;

    public UserEntity toUserRecentSearchEntity() {
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .recentSearch(dongName)
                .build();
        return userEntity;
    }
}
