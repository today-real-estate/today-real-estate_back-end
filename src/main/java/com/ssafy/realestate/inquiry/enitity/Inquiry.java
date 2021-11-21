package com.ssafy.realestate.inquiry.enitity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.realestate.common.BaseTimeEntity;
import com.ssafy.realestate.user.entity.UserAuthority;
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

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "inquiry_answer_id", referencedColumnName = "id")
    @JsonManagedReference(value = "inquiry-inquiryAnswer")
    private InquiryAnswer inquiryAnswer;

}
