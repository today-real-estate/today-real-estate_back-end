package com.ssafy.realestate.likeApt.dto;

import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeAptRequestDto {

    @Id
    private Long userId;

    @NotBlank(message = "아파트 코드가 없습니다.")
    private String aptCode;

    public LikeApt toSaveLikeApt(UserEntity user,LikeAptRequestDto likeAptRequestDto) {
        LikeApt likeApt = LikeApt.builder()
                .user(user)
                .aptCode(likeAptRequestDto.getAptCode())
                .build();
        return likeApt;
    }

}