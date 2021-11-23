package com.ssafy.realestate.notice.dto;


import com.ssafy.realestate.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static NoticeResponseDto from(Notice notice) {
        return new NoticeResponseDto(notice.getId(), notice.getTitle(), notice.getContent(),
                notice.getCreatedDate(),notice.getModifiedDate());
    }
}
