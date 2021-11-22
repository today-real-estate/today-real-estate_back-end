package com.ssafy.realestate.inquiry.dto;

import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerUpdateDto {

    @NotBlank(message = "답글 id가 필요합니다.")
    private Long id;

    @NotBlank(message = "답변 내용을 작성해주세요")
    private String answerContent;

    public InquiryAnswer toUpdateInquiryAnswerEntity() {
        InquiryAnswer inquiryAnswer = InquiryAnswer.builder()
                .id(id)
                .answerContent(answerContent)
                .build();
        return inquiryAnswer;
    }
}
