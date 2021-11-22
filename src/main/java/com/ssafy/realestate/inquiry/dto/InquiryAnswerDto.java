package com.ssafy.realestate.inquiry.dto;

import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerDto {
    @NotBlank(message = "1대1문의 id가 필요합니다.")
    private Long inquiryId;

    @NotBlank(message = "답변 내용을 작성해주세요")
    private String answerContent;

    public InquiryAnswer toSaveInquiryAnswerEntity(Inquiry inquiry){
        InquiryAnswer inquiryAnswer = InquiryAnswer.builder()
                .inquiry(inquiry)
                .answerContent(answerContent)
                .build();
        return inquiryAnswer;
    }
}
