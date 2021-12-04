package com.ssafy.realestate.notice.service;

import com.ssafy.realestate.notice.dto.NoticeRequestDto;
import com.ssafy.realestate.notice.dto.NoticeResponseDto;
import com.ssafy.realestate.notice.dto.NoticeUpdateRequestDto;
import com.ssafy.realestate.notice.entity.Notice;
import com.ssafy.realestate.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<NoticeResponseDto> findAll() {
        List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return notices.stream().map(NoticeResponseDto::from).collect(Collectors.toList());
    }

    public NoticeResponseDto findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항입니다."));
        return NoticeResponseDto.from(notice);
    }

    @Transactional
    public NoticeResponseDto save(NoticeRequestDto noticeRequestDto) {
        return NoticeResponseDto.from(noticeRepository.save(noticeRequestDto.toSaveNotice()));
    }

    @Transactional
    public Long update(NoticeUpdateRequestDto noticeUpdateRequestDto) {
        Notice originNotice = noticeRepository.findById(noticeUpdateRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항입니다."));
        originNotice.update(noticeUpdateRequestDto);
        return originNotice.getId();
    }

    public void deleteById(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다.");
        }
        noticeRepository.deleteById(id);
    }
}
