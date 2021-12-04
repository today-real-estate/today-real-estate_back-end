package com.ssafy.realestate.notice.entity;

import com.ssafy.realestate.common.BaseTimeEntity;
import com.ssafy.realestate.notice.dto.NoticeRequestDto;
import com.ssafy.realestate.notice.dto.NoticeUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    public void update(NoticeUpdateRequestDto noticeUpdateRequestDto) {
        this.title = noticeUpdateRequestDto.getTitle();
        this.content = noticeUpdateRequestDto.getContent();
    }

}
