package com.ssafy.realestate.inquiry.controller;

import com.ssafy.realestate.inquiry.dto.*;
import com.ssafy.realestate.inquiry.service.InquiryService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<InquiryResponseDto>> findByUserId(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(inquiryService.findByUserId(userId));
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<InquiryResponseDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(inquiryService.findById(id));
    }

    @PostMapping("/create")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> save(@RequestBody InquiryRequestDto inquiryRequestDto) {
        InquiryResponseDto postResponseDto = inquiryService.save(inquiryRequestDto);
        return ResponseEntity.created(URI.create("/" + postResponseDto.getId()))
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> update(@RequestBody InquiryUpdateDto inquiryUpdateDto) {
        return ResponseEntity.ok("id :" + inquiryService.update(inquiryUpdateDto).getId() + " 수정완료");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        inquiryService.deleteById(id);
        return ResponseEntity.ok("id :" + id + " 삭제완료");
    }

    @PostMapping("/answer")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerSave(@RequestBody InquiryAnswerDto InquiryAnswerDto) {
        return ResponseEntity.ok("Inquiry ID : " + inquiryService.answerSave(InquiryAnswerDto) + " 답변 저장 완료");
    }

    @DeleteMapping("/answer/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerdelete(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Inquiry ID : " + inquiryService.answerDelete(id) + " 답변 삭제 완료");
    }

    @PutMapping("/answer{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerUpdate(@RequestBody InquiryAnswerUpdateDto inquiryAnswerUpdateDto) {
        return ResponseEntity.ok("Inquiry ID : " + inquiryService.answerUpdate(inquiryAnswerUpdateDto) + " 답변 수정 완료");
    }

}
