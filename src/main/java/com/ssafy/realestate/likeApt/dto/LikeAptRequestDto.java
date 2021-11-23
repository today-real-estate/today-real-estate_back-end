package com.ssafy.realestate.likeApt.dto;

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

    @NotBlank(message = "동 코드가 없습니다.")
    private String aptCode;
}