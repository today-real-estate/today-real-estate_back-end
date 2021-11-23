package com.ssafy.realestate.likeApt.dto;


import com.ssafy.realestate.likeApt.entity.LikeApt;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeAptResponseDto {
    private Long id;
    private String aptCode ;


    public static LikeAptResponseDto from(LikeApt likeApt) {
        return new LikeAptResponseDto(likeApt.getId(),likeApt.getAptCode());
    }
}