package com.ssafy.realestate.notice.dto;

import com.ssafy.realestate.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDto {

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    public Notice toSaveNotice() {
        Notice notice = Notice.builder()
                .title(title)
                .content(content)
                .build();
        return notice;
    }

}
