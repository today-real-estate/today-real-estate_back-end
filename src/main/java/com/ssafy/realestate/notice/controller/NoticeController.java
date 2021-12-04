package com.ssafy.realestate.notice.controller;

import com.ssafy.realestate.notice.dto.NoticeRequestDto;
import com.ssafy.realestate.notice.dto.NoticeResponseDto;
import com.ssafy.realestate.notice.dto.NoticeUpdateRequestDto;
import com.ssafy.realestate.notice.service.NoticeService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> findAll() {
        return ResponseEntity.ok(noticeService.findAll());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<NoticeResponseDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    }

    @PostMapping("/create")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> save(@RequestBody NoticeRequestDto noticeRequestDto) {
        NoticeResponseDto NoticeResponseDto = noticeService.save(noticeRequestDto);
        return ResponseEntity.created(URI.create("/" + NoticeResponseDto.getId()))
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> update(@RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto) {
        return ResponseEntity.created(URI.create("/" +  noticeService.update(noticeUpdateRequestDto)))
                .build();
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        noticeService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
