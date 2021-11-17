package com.ssafy.realestate.inquiry.dto;

import com.ssafy.realestate.inquiry.enitity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryUpdateDto {

    @Id
    private Long id;

    @NotBlank(message = "내용을 입력하세요")
    private String inquiryType;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;


    public Inquiry toUpdateInquiryEntity() {
        Inquiry inquiry = Inquiry.builder()
                .id(id)
                .inquiryType(inquiryType)
                .title(title)
                .content(content)
                .build();
        return inquiry;

    }
}
