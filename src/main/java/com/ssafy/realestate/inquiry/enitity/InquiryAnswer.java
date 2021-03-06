package com.ssafy.realestate.inquiry.enitity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.realestate.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiryAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    @JsonBackReference(value = "inquiry-inquiry_answer")
    private Inquiry inquiry;

    @NotBlank(message = "내용을 입력하세요")
    private String answerContent;

    public void answerUpdate(String answerContent){
        this.answerContent=answerContent;
    }
}
