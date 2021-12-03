package com.ssafy.realestate.user.dto;

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

    @NotBlank(message = "검색어가 없습니다.")
    private String recentSearch;
}
