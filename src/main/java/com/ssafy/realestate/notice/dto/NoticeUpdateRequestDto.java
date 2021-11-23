package com.ssafy.realestate.notice.dto;

import com.ssafy.realestate.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUpdateRequestDto {
    @Id
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    public Notice toUpdateNotice() {
        Notice notice = Notice.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
        return notice;
    }
}
