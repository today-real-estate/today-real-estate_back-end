package com.ssafy.realestate.inquiry.dto;

import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryRequestDto {

    @Positive(message = "사용자 없음")
    private Long userId;

    @NotBlank(message = "문의 유형이 없습니다.")
    private String inquiryType;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    public Inquiry toSaveInquiryEntity(UserEntity user){
        Inquiry inquiry = Inquiry.builder()
                .inquiryType(inquiryType)
                .title(title)
                .content(content)
                .user(user)
                .build();
        return inquiry;
    }

}
