package com.ssafy.realestate.inquiry.controller;

import com.ssafy.realestate.inquiry.dto.*;
import com.ssafy.realestate.inquiry.service.InquiryService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

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
        return new ResponseEntity("id :" + inquiryService.update(inquiryUpdateDto).getId() + " 수정완료",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        inquiryService.deleteById(id);
        return new ResponseEntity("id :" + id + " 삭제완료", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/answer")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerSave(@RequestBody InquiryAnswerDto InquiryAnswerDto) {
        return ResponseEntity.ok("Inquiry Answer ID : " + inquiryService.answerSave(InquiryAnswerDto) + " 답변 저장 완료");
    }

    @DeleteMapping("/answer")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerDelete(@RequestBody Map<String, Long> object) {
        return ResponseEntity.ok("Inquiry Answer ID : " + inquiryService.answerDelete(object.get("id"), object.get("inquiryId")) + " 답변 삭제 완료");
    }

    @PutMapping("/answer")
    @PreAuthorize(roles = {"ROLE_ADMIN"})
    public ResponseEntity<String> inquiryAnswerUpdate(@RequestBody InquiryAnswerUpdateDto inquiryAnswerUpdateDto) {
        return ResponseEntity.ok("Inquiry Answer ID : " + inquiryService.answerUpdate(inquiryAnswerUpdateDto) + " 답변 수정 완료");
    }

}
