package com.ssafy.realestate.inquiry.enitity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.realestate.common.BaseTimeEntity;
import com.ssafy.realestate.inquiry.dto.InquiryUpdateDto;
import com.ssafy.realestate.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Inquiry extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-inquiry")
    private UserEntity user;

    @NotBlank(message = "문의 유형이 없습니다.")
    private String inquiryType;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    private boolean isComplete;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", referencedColumnName = "id")
    @JsonManagedReference(value = "inquiry-inquiry_answer")
    private List<InquiryAnswer> inquiryAnswer;

    public void updateInquiry(InquiryUpdateDto inquiryUpdateDto){
        this.title = inquiryUpdateDto.getTitle();
        this.content = inquiryUpdateDto.getContent();
    }
    public void isCompleteInquiryUpdate(boolean isComplete){
        this.isComplete = isComplete;
    }
}
