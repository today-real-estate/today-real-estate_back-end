package com.ssafy.realestate.inquiry.dto;

import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class InquiryResponseDto {
    private Long id;
    private String title;
    private String content;
    private String inquiryType;
    private boolean isComplete;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<InquiryAnswer> inquiryAnswer;

    public static InquiryResponseDto from(Inquiry inquiry) {
        return new InquiryResponseDto(inquiry.getId(), inquiry.getTitle(), inquiry.getContent(), inquiry.getInquiryType(), inquiry.isComplete(),
                inquiry.getCreatedDate(), inquiry.getModifiedDate(), inquiry.getInquiryAnswer());
    }
}
